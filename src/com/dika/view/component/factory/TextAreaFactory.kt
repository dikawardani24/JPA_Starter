package com.dika.view.component.factory

import com.dika.view.component.TextArea

object TextAreaFactory : TextComponentFactory<TextArea>() {
    override val inputPromptField: (String) -> TextArea
        get() = fun(inputPrompt: String): TextArea {
            return create().apply {
                setInputPrompt(inputPrompt)
            }
        }

    override fun create(): TextArea {
        return TextArea()
    }

}