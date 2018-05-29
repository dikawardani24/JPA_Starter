package com.dika.view.component

import com.dika.ClearAble
import javax.swing.JComboBox

open class ComboBox<T>: JComboBox<T>(), ClearAble {

    fun addItems(list: List<T>) {
        list.forEach { addItem(it) }
    }

    fun addItems(vararg items: T) {
        addItems(items.toList())
    }

    override val isEmpty: Boolean
        get() = itemCount <= 0

    override fun clear() {
        removeAllItems()
    }
}