package com.dika.view.component.custom

import com.dika.res.SmallIconRes
import com.dika.view.component.ImageHolder
import com.dika.view.component.PasswordField

class RequirePasswordField: PasswordField() {
    init {
        trailingComponent = ImageHolder(SmallIconRes.requireIcon)
    }
}