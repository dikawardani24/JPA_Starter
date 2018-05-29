package com.dika.view.component

import com.alee.laf.button.WebButton
import com.dika.res.FontResource
import com.dika.res.SmallIconRes
import java.awt.Color

open class Button: WebButton() {
    init {
        font = FontResource.alex
        fontSize = 14
        isBoldFont = true
        isDrawShade = true
        foreground = Color.BLACK
        shadeColor = Color.WHITE
        rolloverIcon = SmallIconRes.dotIcon
        round = 5
    }
}