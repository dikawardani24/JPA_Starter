package com.dika.view.component

import com.alee.laf.label.WebLabel
import com.dika.ClearAble
import com.dika.res.FontResource
import java.awt.Color

open class Label: WebLabel(), ClearAble {
    init {
        font = FontResource.cardo
        fontSize = 14
        foreground = Color.BLACK
    }

    override fun clear() {
        text = ""
    }

    override val isEmpty: Boolean
        get() = text.isEmpty()
}