package com.dika.view.custom

import com.dika.view.Container
import com.dika.view.InputContainer
import com.dika.view.component.*

interface PagingTableView: Container<Panel> {
    val table: Table
    val nextButton: Button
    val previousButton: Button
    val firstPageButton: Button
    val lastPageButton: Button
    val refreshButton: Button
    val pageLabel: Label
    val totalDataField: TextField
    val titleDataLabel: Label
}