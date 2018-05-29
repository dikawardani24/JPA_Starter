package com.dika.view.component.custom

import com.dika.res.SmallIconRes
import com.dika.view.component.ImageHolder
import com.dika.view.component.TextField

class UnEditableTextField: TextField() {
    init {
        isEditable = false
    }
}

class RequireTextField: TextField() {
    init {
        trailingComponent = ImageHolder(SmallIconRes.requireIcon)
    }
}