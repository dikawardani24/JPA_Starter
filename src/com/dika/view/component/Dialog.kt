package com.dika.view.component

import com.alee.laf.rootpane.WebDialog
import com.dika.res.SmallIconRes
import javax.swing.WindowConstants

open class Dialog: WebDialog {
    constructor(): super()

    constructor(title: String): this() {
        this.title = title
    }

    init {
        glassPane = GlassPanel()
        iconImages = listOf(SmallIconRes.appsIcon.image)
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
    }
}