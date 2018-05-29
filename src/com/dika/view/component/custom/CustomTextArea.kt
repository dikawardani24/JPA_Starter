package com.dika.view.component.custom

import com.dika.view.component.TextArea

class UnEditableTextArea: TextArea() {
    init {
        isEditable = false
    }
}