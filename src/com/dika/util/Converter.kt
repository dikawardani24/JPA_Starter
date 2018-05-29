package com.dika.util

internal abstract class Converter<in T> {
    internal abstract fun convert(value: T)
}