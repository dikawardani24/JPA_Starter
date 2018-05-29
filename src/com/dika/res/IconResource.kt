package com.dika.res

import com.dika.Logger
import javax.swing.ImageIcon

open class IconResource(path: String): Resource<ImageIcon>(path) {
    override fun produce(filename: String): ImageIcon {
        return try {
            val resource = javaClass.classLoader.getResource("META-INF/$path$filename")
            ImageIcon(resource)
        } catch (e: Exception) {
            Logger.printError(e)
            throw e
        }
    }
}