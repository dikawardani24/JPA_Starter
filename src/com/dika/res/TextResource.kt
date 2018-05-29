package com.dika.res

import com.dika.Logger

open class TextResource(path: String, val type: String) : Resource<String>(path) {
    override fun produce(filename: String): String {
        return try {
            val text = StringBuilder()
            val inputStream = javaClass.classLoader.getResourceAsStream("META-INF/$path$filename")
            var byteRead: Int
            do {
                byteRead = inputStream.read()
                text.append(byteRead.toChar())
            } while (byteRead != -1)
            text.substring(0, text.length - 1)
        } catch (ex: Exception) {
            Logger.printError(ex)
            ""
        }
    }
}