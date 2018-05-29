package com.dika.database

import com.dika.database.exception.ConnectionException
import com.dika.database.exception.NotFoundException

interface DatabaseService<P: Number, E: AbstractEntity<P>> {

    /**
     * Insert new data of the [E] to the database
     * @param   model a entity to be inserted
     */
    @Throws(ConnectionException::class)
    fun create(model: E)

    /**
     * Update the data of the [E] and saveIcon the change in database
     * @param   model an instance of [E] which is wished to be updated
     */
    @Throws(ConnectionException::class)
    fun update(model: E)

    /**
     * Destroy the data of the [E] from the database
     * @param   model a entity to be destroyed
     */
    @Throws(ConnectionException::class)
    fun destroy(model: E)

    /**
     * Count the total data of the [E] from database
     * @return  `>0` if there is data of the entity;
     *          `0` otherwise.
     */
    @Throws(ConnectionException::class)
    fun count(): Int

    /**
     * Find the [E] base on the given primary key
     * @param   primaryKey the value [primaryKey] of the [E] where the type of [primaryKey] is [P]
     * @return  instance of [E];
     *          `null` otherwise.
     */
    @Throws(ConnectionException::class, NotFoundException::class)
    fun findBy(primaryKey: P): E

    /**
     * Find all data of the [E] from the database
     * @return  list instance of [E] if data are found;
     *          `emptyList` otherwise.
     */
    @Throws(ConnectionException::class)
    fun findAll(): List<E>

    /**
     * Find all data of the [E] from the database
     * with the limit of [maxResults] to be fetched from the database
     * and start fetching data from [firstResult]
     * @param   maxResults the limit data to be fetched
     * @param   firstResult the start point to fetching data
     * @return  list instance of [E] if data are found;
     *          `emptyList` otherwise.
     */
    @Throws(ConnectionException::class)
    fun findAll(maxResults: Int, firstResult: Int): List<E>
}