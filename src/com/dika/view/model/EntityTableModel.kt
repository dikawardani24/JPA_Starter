package com.dika.view.model

import com.alee.laf.table.WebTable
import com.dika.SystemException
import com.dika.database.AbstractEntity

abstract class EntityTableModel<T: AbstractEntity<*>>(private val tableHolder: WebTable):
    TableModel(){
    val entities = ArrayList<T>()

    init {
        tableHolder.model = this
    }

    override fun clear() {
        super.clear()
        entities.clear()
    }

    protected abstract fun initColumns()

    protected abstract fun onInsertNewRow(entity: T)

    protected abstract fun onUpdateRow(entity: T)

    fun update(entity: T) {
        onUpdateRow(entity)

        entities.iterator().run {
            while (hasNext()) {
                if (next().id == entity.id) {
                    remove()
                }
            }
        }
        entities.add(entity)
    }

    fun insert(entity: T) {
        onInsertNewRow(entity)
        entities.add(entity)
    }

    fun insert(entities: List<T>) {
        for (entity in entities) insert(entity)
    }

    fun update(entities: List<T>) {
        for (entity in entities) update(entity)
    }

    fun findIndexOf(entity: T): Int {
        for (i in 0 until rowCount) {
            val id = getValueAt(i, 0)
            if (id == entity.id) {
                return i
            }
        }

        throw SystemException("Couldn't find $entity on this table")
    }

    fun findPositionOf(entity: T): Int {
        return findIndexOf(entity) + 1;
    }
 }