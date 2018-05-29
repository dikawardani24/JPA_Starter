package com.dika.view.component

import com.alee.laf.text.WebTextArea
import com.dika.ClearAble
import java.awt.Insets

open class TextArea: WebTextArea(), ClearAble {

    init {
        margin = Insets(0, 5, 0, 5)
    }

    override val isEmpty: Boolean
        get() = text.isEmpty()

}