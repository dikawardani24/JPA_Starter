package com.dika.activity

import com.dika.Logger
import com.dika.Manager
import kotlin.reflect.KClass

object ActivityManager {
    private val manager = Manager<Class<*>, Activity<*>>()

    fun<A: Activity<*>> load(activityClass: Class<A>): A {
        Logger.printInfo("Starting $activityClass")
        manager.load(activityClass).let {
            @Suppress("UNCHECKED_CAST")
            return when(it) {
                null -> {
                    Logger.printInfo("Load new $activityClass")
                    val value = activityClass.newInstance()
                    manager.add(activityClass, value)
                    value
                }
                else -> {
                    Logger.printInfo("Resume $activityClass from $javaClass")
                    it as A
                }
            }
        }
    }

    fun<A: Activity<*>> load(activityClass: KClass<A>): A {
        return load(activityClass.java)
    }
}