package com.dika.view.custom

import com.alee.laf.menu.WebMenuItem
import com.dika.view.View
import com.dika.view.component.Button
import com.dika.view.component.Frame
import com.dika.view.component.custom.HeaderLabel

interface CommonManagerView : View<Frame> {
    val addButton: Button
    val printButton: Button
    val pagingTableView: PagingTableView
    val updateMenuItem: WebMenuItem
    val deleteMenuItem: WebMenuItem
    val headerLabel: HeaderLabel
}