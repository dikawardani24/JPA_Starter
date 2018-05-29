package com.dika

abstract class Factory<out T> {
    abstract fun create(): T
}