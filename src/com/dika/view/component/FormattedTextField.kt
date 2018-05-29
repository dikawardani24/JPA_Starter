package com.dika.view.component

import com.alee.laf.text.WebFormattedTextField
import com.dika.ClearAble
import java.awt.Insets

open class FormattedTextField : WebFormattedTextField(), ClearAble {
    var type: Type = Type.DECIMAL

    init {
        round = 5
        margin = Insets(0, 5, 0, 5)
    }

    override val isEmpty: Boolean
        get() {
            return when(type) {
                Type.CURRENCY,
                Type.DECIMAL -> value.toString().toDouble() <= 0
            }
        }

    override fun clear() {
        when (type) {
            Type.CURRENCY,
            Type.DECIMAL -> value = 0
        }
    }

    enum class Type {
        CURRENCY, DECIMAL
    }
}