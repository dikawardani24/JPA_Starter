package com.dika.util.formatter

import com.dika.util.Formatter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Format style of calendar base on givem pattern
 * @param   T type of [Date] to be formatted
 * @author  Dika Wardani
 */
internal class CalendarFormatter<T: Date>: Formatter<T>() {
    /**
     *Format [Date] to for EE, D MMMM yyyy
     * @param   value the [Date] to be formatted
     * @return  `a formatted date in String`
     */
    override fun format(value: T) :String = format(value, "EEEE, d MMMM yyyy")

    /**
     * Get hour from a [Date]
     * @param   value the [Date] to be takken its hour
     * @return  `a hour in string with format HH:mm:ss`
     */
    internal fun toHour(value: T): String = format(value, "HH:mm:ss")

    /**
     * Format [Date] base on pattern in IN code locale
     * @param   value the [Date] to be formatted
     * @return  a formatted [Date] in string
     */
    private fun format(value: T, pattern: String): String {
        val locale = Locale("IN")
        SimpleDateFormat(pattern, locale).run { return format(value) }
    }
}