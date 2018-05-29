package com.dika.view.component

import com.alee.global.StyleConstants
import com.alee.laf.panel.WebPanel
import java.awt.BorderLayout
import java.awt.Insets

open class Panel: WebPanel() {
    init {
        isUndecorated = false
        layout = BorderLayout()
        margin = Insets(10, 10, 10, 10)
        round = StyleConstants.decorationRound
        isPaintFocus = true
    }
}