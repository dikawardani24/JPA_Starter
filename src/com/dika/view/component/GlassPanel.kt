package com.dika.view.component

import com.alee.laf.panel.WebPanel
import com.dika.Logger
import com.dika.res.BigIconRes
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class GlassPanel : WebPanel() {
    init {
        isOpaque = false
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                Logger.printInfo("Clicked on X : ${e?.x}, Y : ${e?.y}")
            }
        })

        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                super.keyPressed(e)
                Logger.printInfo("Pressed key : ${e?.keyCode}")
            }
        })
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val logo = BigIconRes.appsLogo.image
        val point = Point(width - logo.getWidth(null),
                height - logo.getHeight(null))
        val paint = GradientPaint(0f, 0f, Color.BLACK,
                width.toFloat(), height.toFloat(), Color.BLACK)

        (g?.create() as Graphics2D).apply {
            this.paint = paint
            composite = AlphaComposite.SrcOver.derive(0.3f)
            fillRect(0, 0, width, width)
            drawImage(logo, point.x, point.y, null)
            dispose()
        }
    }
}