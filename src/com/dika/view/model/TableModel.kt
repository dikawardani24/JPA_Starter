package com.dika.view.model

import com.dika.ClearAble
import javax.swing.table.DefaultTableModel

open class TableModel: DefaultTableModel(), ClearAble {

    override fun clear() {
        rowCount = 0
    }

    override val isEmpty: Boolean
        get() = rowCount == 0

    protected fun addColumns(vararg columnNames: String) {
        columnNames.forEach { columnName -> addColumn(columnName) }
    }
}