package com.dika.view.component

import com.alee.laf.table.WebTable

class Table: WebTable() {
    init {
        autoCreateRowSorter = true
        autoCreateColumnsFromModel = true
        setFontStyle(true, true)
    }
}