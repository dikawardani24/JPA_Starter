package com.dika.util

import com.dika.Logger
import com.dika.util.formatter.NumberFormatter

object NumberHelper {
    fun toCurrency(value: Double?): String =
            if (value == null) "null" else "Rp ${NumberFormatter().format(value)}"

    fun toInt(value: Any?): Int {
        if (value == null) return 0
        return try {
            Integer.parseInt(value.toString())
        } catch (e: NumberFormatException) {
            Logger.printInfo("Couldn't convert to int with input value \"$value\", operation will return 0")
            0
        }
    }

    fun toDouble(value: Any?): Double {
        if (value == null) return 0.0
        return try {
            java.lang.Double.parseDouble(value.toString())
        } catch (e: NumberFormatException) {
            Logger.printInfo("Couldn't convert to double with input value \"$value\", operation will return 0.0")
            0.0
        }
    }
}