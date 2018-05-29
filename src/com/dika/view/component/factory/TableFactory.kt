package com.dika.view.component.factory

import com.dika.Factory
import com.dika.view.component.Table
import javax.swing.ListSelectionModel

object TableFactory: Factory<Table>() {
    override fun create(): Table {
        return Table()
    }

    val singleSelectionTable get() = create().apply {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
    }
}