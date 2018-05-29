package com.dika.res

import com.dika.Logger
import java.awt.Font

object FontResource: Resource<Font>("font/") {
    override fun produce(filename: String): Font {
        javaClass.classLoader.getResourceAsStream("META-INF/$path$filename").run {
            return try {
                Font.createFont(Font.TRUETYPE_FONT, this)
            } catch (ex: Exception) {
                Logger.printError(ex)
                Font.getFont(Font.MONOSPACED)
            }
        }
    }

    val cardo get() = load("Cardo.ttf")
    val alex get() = load("Alex.ttf")
    val calluna get() = load("Calluna.ttf")
    val explora get() = load("Explora.ttf")
}