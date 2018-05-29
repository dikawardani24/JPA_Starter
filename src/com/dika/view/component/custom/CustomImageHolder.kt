package com.dika.view.component.custom

import com.dika.res.BigIconRes
import com.dika.res.SmallIconRes
import com.dika.view.component.ImageHolder
import java.awt.Dimension
import java.awt.Image
import javax.swing.ImageIcon

class CompanyLogoImageHolder: ImageHolder() {
    init {
        val logoImage = BigIconRes.logoPerusahaan.image.getScaledInstance(400, 140, Image.SCALE_SMOOTH)
        setIcon(ImageIcon(logoImage))
        preferredSize = Dimension(500, 200)
    }
}

class UserLogoImageHolder: ImageHolder() {
    init {
        setIcon(SmallIconRes.userIcon)
        preferredSize = Dimension(50, 50)
    }
}

class PasswordImageHolder: ImageHolder() {
    init {
        setIcon(SmallIconRes.keyBlueIcon)
        preferredSize = Dimension(50, 50)
    }
}

class AppLogoImageHolder: ImageHolder() {
    init {
        val logoImage = BigIconRes.appsLogo.image.getScaledInstance(78, 78, Image.SCALE_SMOOTH)
        setIcon(ImageIcon(logoImage))
        preferredSize = Dimension(90, 90)
    }
}

class UnivLogoImageHolder: ImageHolder() {
    init {
        val logoImage = BigIconRes.univIconPng.image.getScaledInstance(140, 140, Image.SCALE_SMOOTH)
        setIcon(ImageIcon(logoImage))
        preferredSize = Dimension(500, 200)
    }
}