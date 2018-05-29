package com.dika.util.formatter

import com.dika.util.Formatter
import java.text.NumberFormat

internal class NumberFormatter : Formatter<Number>() {
    override fun format(value: Number): String = NumberFormat.getNumberInstance().format(value)
}