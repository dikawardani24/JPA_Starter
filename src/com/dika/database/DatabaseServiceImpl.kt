package com.dika.database

import com.dika.System
import com.dika.SystemException
import com.dika.database.exception.ConnectionException
import com.dika.database.exception.NotFoundException
import com.dika.database.exception.UnloadPersistenceException
import org.eclipse.persistence.exceptions.DatabaseException
import java.util.*
import javax.persistence.*
import kotlin.reflect.KClass

abstract class DatabaseServiceImpl<P : Number, E : AbstractEntity<P>> : DatabaseService<P, E> {
    protected abstract val entityKClass: Class<E>
    private var emf: EntityManagerFactory? = null

    override fun create(model: E) {
        transaction { persist(model) }
    }

    override fun update(model: E) {
        onExist(model) {
            merge(model)
        }
    }

    override fun destroy(model: E) {
        onExist(model) {
            remove(it)
        }
    }

    override fun count(): Int {
        return execute {
            criteriaBuilder.createQuery().run {
                select(criteriaBuilder.count(from(entityKClass)))
                createQuery(this).run {
                    (singleResult as Long).toInt()
                }
            }
        }
    }

    override fun findBy(primaryKey: P): E {
        return execute {
            find(entityKClass, primaryKey).let {
                when(it) {
                    null -> throw NotFoundException("Couldn't find data of ${entityKClass.simpleName} " +
                            "with primary key : $primaryKey")
                    else -> it
                }
            }
        }
    }

    override fun findAll(): List<E> {
        return findAll(true)
    }

    override fun findAll(maxResults: Int, firstResult: Int): List<E> {
        return findAll(false, maxResults, firstResult)
    }

    protected fun findByNamedQuery(namedQuery: String, parameters: (map: HashMap<String, Any>)-> Map<String, Any>): E {
        return findByNamedQuery(namedQuery, parameters.invoke(HashMap()))
    }

    protected fun findByNamedQuery(namedQuery: String, parameters: Map<String, Any>): E {
        return execute(namedQuery, entityKClass, parameters) {
            singleResult
        }
    }

    protected fun findsByNamedQuery(namedQuery: String, parameters: (map: HashMap<String, Any>)-> Map<String, Any>): List<E> {
        return findsByNamedQuery(namedQuery, parameters.invoke(HashMap()), true)
    }

    protected fun findsByNamedQuery(namedQuery: String, maxResults: Int, firstResult: Int, parameters: (map: HashMap<String, Any>)-> Map<String, Any>): List<E> {
        return findsByNamedQuery(namedQuery, parameters.invoke(HashMap()), false, maxResults, firstResult)
    }

    protected fun findsByNamedQuery(namedQuery: String, parameters: Map<String, Any>): List<E> {
        return findsByNamedQuery(namedQuery, parameters, true)
    }

    protected fun findsByNamedQuery(namedQuery: String, parameters: Map<String, Any>, maxResults: Int, firstResult: Int): List<E> {
        return findsByNamedQuery(namedQuery, parameters, false, maxResults, firstResult)
    }

    protected fun countByNamedQuery(namedQuery: String, parameters: Map<String, Any>): Int {
        return execute(namedQuery, Long::class.java, parameters) {
            singleResult.toInt()
        }
    }

    protected fun countByNamedQuery(namedQuery: String, parameters: (map: HashMap<String, Any>)-> Map<String, Any>): Int {
        return execute(namedQuery, Long::class.java, parameters.invoke(HashMap())) {
            singleResult.toInt()
        }
    }


    protected fun <R: Any>execute(query: String, result: KClass<R>,block: EntityManager.() -> R): R {
        return transaction {
            createQuery(query, result.java)
            block()
        }
    }

    private fun findsByNamedQuery(namedQuery: String, parameters: Map<String, Any>, all: Boolean, maxResults: Int = 1, firstResult: Int= 1): List<E> {
        return execute(namedQuery, entityKClass, parameters) {
            if (!all) atRange(maxResults, firstResult)
            resultList
        }
    }

    /**
     * Load all rows or limit the total result rows from database from the specified table name
     * @param all Set this value to true if you wish to load all rows, param maxResults and firstResult will be ignored
     *            when the value is true
     * @param maxResults The limit the result rows from database
     * @param firstResult The starting point of row on getting data from database
     */
    private fun findAll(all: Boolean, maxResults: Int = 1, firstResult: Int = 1): List<E> {
        return execute {
            criteriaBuilder.createQuery(entityKClass).run {
                select(from(entityKClass))
                createQuery(this).run {
                    if (!all) {
                        atRange(maxResults, firstResult)
                    }
                    resultList
                }
            }
        }
    }

    /**
     * Assign parameter value to the clause WHERE inside a query
     * @param   parameters the map of the parameter
     * @author  dikawardani24@gmail.com
     */
    private fun Query.assignParameters(parameters: Map<String, Any>) {
        parameters.keys.forEach {
            val value = parameters[it]

            value?.run {
                if (this is Date) setParameter(it, this, TemporalType.TIMESTAMP)
                else setParameter(it, parameters[it])
            }
        }
    }

    /**
     * Set max result accepted from the query and set first index row to searchIcon
     * @param   maxResults the max result to accept
     * @param   firstResult the start row to find data
     * @throws  [SystemException] if max result or first result is set to <= 0
     * @author  dikawardani24@gmail.com
     */
    private fun Query.atRange(maxResults: Int, firstResult: Int) {
        if (maxResults < 0) throw SystemException("Max Result must greater than 0")
        if (firstResult < 0) throw SystemException("First Result must greater than 0")

        this.maxResults = maxResults
        this.firstResult = firstResult
    }

    /**
     * Open transaction from [EntityManager] and execute blockIcon function then
     * accept its result as [R] type.
     * This function will commit automatically if the current transaction
     * is not set as rollback only.
     * @param   run the function to be executed
     * @return  the instance of [R]
     * @author  dikawardani24@gmail.com
     */
    protected fun <R> transaction(run: EntityManager.() -> R): R {
        return execute {
            transaction.run {
                begin()
                run().apply {
                    if (!rollbackOnly) commit()
                }
            }
        }
    }

    /**
     * Manipulate the data of [E] if the data is still exist in database server.
     * The onExist operation will be applied by execute blockIcon function and
     * the blockIcon function itself would receive the existence data of [E]
     * @param   model the model to be onExist
     * @param   run the function to be executed in manipulating data
     * @throws  NotFoundException if the model of [E] is not exist on database server
     * @author  dikawardani24@gmail.com
     */
    private fun onExist(model: E, run: EntityManager.(model: E) -> Unit) {
        return transaction { 
            try {
                run(getReference(entityKClass, model.id))
            } catch (en: EntityNotFoundException) {
                throw NotFoundException(model)
            }
        }
    }

    /**
     * Execute the query from the specified named query and return the result as [R].
     * This function will take the last code inside function blockIcon as returning value which is
     * declared as [R] type.
     * @param   C the desire result from the query
     * @param   R the desire result from the function blockIcon
     * @param   namedQuery the name query to be executed
     * @param   result the desire result class from the specified query
     * @param   parameters the paramater for the where clause inside query
     * @param   block the function to be run after the query is executed
     * @return  an instance of [R]
     * @author  dikawardani24@gmail.com
     */
    private fun <C, R> execute(namedQuery: String, result: Class<C>, parameters: Map<String, Any>,
                               block: TypedQuery<C>.() -> R): R {
        return execute {
            createNamedQuery(namedQuery, result).run {
                assignParameters(parameters)
                block()
            }
        }
    }

    /**
     * Executing [EntityManager] operation using current actice [EntityManagerFactory] where the result type
     * from that operation is [R]. After the operation has been executed, the [EntityManagerFactory] will be closed immediately.
     * This function will throw [ConnectionException] if the current [EntityManagerFactory]
     * couldn't established connection between application and database server
     * @param   run the function to be executed
     * @return  instance of [R]
     * @throws  [UnloadPersistenceException] if [Persistence] couldn't create [EntityManagerFactory] using the given
     *          persistence config name.
     * @throws  [ConnectionException] if failed to connect to database server
     * @throws  [SystemException] if the [Persistence] thrown an unknown exeception and its exception would be cause
     *          of the [SystemException]
     * @author  dikawardani24@gmail.com
     */
    private fun <R> execute(run: EntityManager.() -> R): R {
        if (emf == null) {
            emf = loadFactory(System.persitenceName)
        }

        emf.let {
            if (it == null) throw UnloadPersistenceException()

            it.run {
                return try {
                    createEntityManager().run()
                } catch (ex: PersistenceException) {
                    ex.cause?.run {
                        when(this) {
                            is DatabaseException -> {
                                if (isCommunicationFailure) {
                                    throw ConnectionException(this)
                                }
                            }
                        }
                    }

                    when(ex) {
                        is NoResultException -> {
                            throw com.dika.database.exception.NoResultException(ex)
                        }
                    }
                    throw ex
                } finally {
                    if (isOpen) close()
                }
            }
        }
    }

    /**
     * Create [EntityManagerFactory] from [Persistence] if the current entity manager factory still null
     * otherwise use the last one
     * @param   persistenceName the name of persistence config file
     * @return  an instance of [EntityManagerFactory] if no [PersistenceException] is being thrown
     *          null otherwise
     * @throws  [SystemException] if the given persistence config name is null
     * @since   JPA Swing Kotlin version 1.0
     * @author  dikawardani24@gmail.com
     */
    private fun loadFactory(persistenceName: String?): EntityManagerFactory? {
        persistenceName.let {
            if (it == null) throw SystemException("Persistence unit name must not be null", NullPointerException())

            it.run {

                return try {
                    Persistence.createEntityManagerFactory(this)
                } catch (pe: PersistenceException) {
                    null
                }
            }
        }
    }
}