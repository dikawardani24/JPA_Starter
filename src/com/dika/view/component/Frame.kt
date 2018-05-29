package com.dika.view.component

import com.alee.laf.rootpane.WebFrame
import com.dika.res.SmallIconRes
import javax.swing.WindowConstants

open class Frame: WebFrame {
    constructor() : super()

    constructor(title: String): this() {
        this.title = title
    }

    init {
        glassPane = GlassPanel()
        iconImage = SmallIconRes.appsIcon.image
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
    }
}