package com.dika.view.component

import com.alee.extended.window.WebPopOver

class PopOver: WebPopOver() {
    init {
        transparency = 1f
        isAlwaysOnTop = true
        isMovable = false
    }
}