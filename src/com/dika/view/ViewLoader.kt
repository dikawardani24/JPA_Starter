package com.dika.view

import com.dika.SystemException

object ViewLoader {
    var views: List<Class<View<*>>> = emptyList()

    inline fun <reified V: View<*>>load(expected: Class<V>): V {
        views.find {
            it == expected
        }.let {
            when(it) {
                null -> throw SystemException("Couldn't load view $expected")
                else -> {
                    if (it.isInterface) throw SystemException("Couldn't instanciate an interface")

                    val view = it.newInstance()
                    view.let {
                        if (it is V) return it
                        else throw SystemException("Couldn't cast to $expected")
                    }
                }
            }
        }
    }
}