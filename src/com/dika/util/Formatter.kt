package com.dika.util

internal abstract class Formatter<in T> {
    abstract fun format(value: T): String
}