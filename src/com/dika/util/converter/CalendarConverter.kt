package com.dika.util.converter

import com.dika.util.Converter
import java.sql.Timestamp
import java.util.*

/**
 * Convert [Date] to other type of time
 * @sample  startOfToday
 * @author  Dika Wardani
 */
internal class CalendarConverter: Converter<Date>() {
    var calendar: Calendar = Calendar.getInstance()
    val date: Date get() = calendar.time
    val sqlDate: java.sql.Date get() = java.sql.Date(date.time)
    val timeStamp: Timestamp get() = Timestamp(sqlDate.time)

    override fun convert(value: Date) {
        calendar.time = value
    }
}