package com.dika.view.component

import com.alee.laf.text.WebTextField
import com.dika.ClearAble
import java.awt.Insets

open class TextField: WebTextField(), ClearAble {
    init {
        round = 5
        margin = Insets(0, 5, 0, 5)
    }

    override val isEmpty: Boolean
        get() = text.isEmpty()
}