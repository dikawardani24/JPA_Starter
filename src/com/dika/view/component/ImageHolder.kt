package com.dika.view.component

import com.alee.extended.image.WebImage
import javax.swing.ImageIcon

open class ImageHolder: WebImage {
    constructor(): super()
    constructor(icon: ImageIcon) : super(icon)
}