package com.dika.view.component

import com.alee.laf.menu.MenuBarStyle
import com.alee.laf.menu.WebMenuBar

class MenuBar: WebMenuBar() {
    init {
        isUndecorated = true
        menuBarStyle = MenuBarStyle.standalone
    }
}