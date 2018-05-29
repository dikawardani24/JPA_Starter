package com.dika.util

import java.util.*

object CollectionHelper {
    fun <T> collectAsArrayList(items: ArrayList<T>.() -> Unit): ArrayList<T> {
        return ArrayList<T>().apply {
            items()
        }
    }

    fun <T> collectAsArrayList(vararg items: T): ArrayList<T> {
        return ArrayList<T>().apply {
            items.forEach {
                add(it)
            }
        }
    }
}