package com.dika.view

import com.dika.view.component.Button
import com.dika.view.component.Dialog
import com.dika.view.component.custom.HeaderLabel

interface ManipulateView: InputView<Dialog> {
    val clearButton: Button
    val cancelButton: Button
    val saveButton: Button

    val headerLabel: HeaderLabel
}