package com.dika.res

import com.dika.Logger
import com.dika.Manager

/**
 * This class is meant to be a helper for produce resource file
 * from the specified path and file name
 * @param   T the type return of resource
 */
abstract class Resource<out T: Any>(protected val path: String) {
    private val manager = Manager<String, T>()

    /**
     * Produce new resource file which the type is [T]
     * @param   filename the filename to produce
     */
    protected abstract fun produce(filename: String): T

    fun load(filename: String): T {
        Logger.printInfo("Load file with name $filename from $path")
        manager.load(filename).let {
            return when(it) {
                null -> {
                    val value = produce(filename)
                    manager.add(filename, value)
                    value
                }
                else -> it
            }
        }
    }
}