package com.dika.view.component.factory

import com.dika.view.component.PasswordField

object PasswordFieldFactory: TextComponentFactory<PasswordField>() {
    override val inputPromptField: (String) -> PasswordField
        get() = fun(inputPrompt: String): PasswordField {
            return create().apply {
                setInputPrompt(inputPrompt)
            }
        }

    override fun create(): PasswordField {
        return PasswordField()
    }
}