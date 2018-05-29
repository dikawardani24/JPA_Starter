package com.dika.view.component.factory

import com.alee.laf.label.WebLabel
import com.dika.Factory
import com.dika.view.component.Label

object LabelFactory: Factory<Label>() {
    override fun create(): Label {
        return Label()
    }

    fun create(text: String): Label {
        return create().apply { this.text = text }
    }

    val headerLabel get() = create().apply {
        fontSize = 20
        setBoldFont()
    }

    val midHeaderLabel get() = create().apply {
        fontSize = 16
        setBoldFont()
    }
}