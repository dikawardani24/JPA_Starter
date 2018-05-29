package com.dika.util

import com.dika.util.converter.CalendarConverter
import com.dika.util.formatter.CalendarFormatter
import java.util.*
import java.util.concurrent.TimeUnit

object CalendarHelper {
    fun <T : Date> dateToLocal(date: T?): String = if (date == null) "null" else CalendarFormatter<T>().format(date)

    fun <T : Date> getHourOf(date: T?): String = if (date == null) "null" else CalendarFormatter<T>().toHour(date)

    infix fun Date.plus(howManyDaysToAdd: Int): Date {
        Calendar.getInstance().run {
            time = this@plus
            if (howManyDaysToAdd == 0) return this.time

            val currentDay = get(Calendar.DAY_OF_MONTH) + howManyDaysToAdd
            set(Calendar.DAY_OF_MONTH, currentDay)

            return this.time
        }
    }

    infix fun Date.min(date: Date): Long {
        val howManyDays = Math.abs(this.time - date.time)
        return TimeUnit.MICROSECONDS.toDays(howManyDays)
    }

    fun startOfToday(): Date {
        Calendar.getInstance().run {
            set(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DATE), 0, 0, 0)
            CalendarConverter().run {
                convert(time)
                return date
            }
        }
    }

    fun endOfToday(): Date {
        Calendar.getInstance().run {
            set(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DATE), 23, 59, 59)
            CalendarConverter().run {
                convert(time)
                return date
            }
        }
    }

    fun today(): Date = Calendar.getInstance().time
}