package com.dika.view.component.factory

import com.dika.view.component.TextField

object TextFieldFactory: TextComponentFactory<TextField>() {
    override val inputPromptField: (String) -> TextField
        get() = fun(inputPrompt: String): TextField {
            return create().apply {
                setInputPrompt(inputPrompt)
            }
        }

    override fun create(): TextField {
        return TextField()
    }
}