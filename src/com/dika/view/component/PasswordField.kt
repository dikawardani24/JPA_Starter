package com.dika.view.component

import com.alee.laf.text.WebPasswordField
import com.dika.ClearAble
import java.awt.Insets

open class PasswordField: WebPasswordField(), ClearAble {

    init {
        round = 10
        margin = Insets(0, 5, 0, 5)
    }

    override val isEmpty: Boolean
        get() = password.isEmpty()

    fun showPassword(show: Boolean) {
        echoChar = if (show) 0.toChar() else '*'
    }
}