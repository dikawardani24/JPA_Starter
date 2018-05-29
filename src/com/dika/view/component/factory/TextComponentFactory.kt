package com.dika.view.component.factory

import com.dika.Factory
import javax.swing.text.JTextComponent

abstract class TextComponentFactory<T: JTextComponent>: Factory<T>() {

    val uneditableField get() = create().apply {
        isEditable = false
    }

    abstract val inputPromptField: (String) -> T
}