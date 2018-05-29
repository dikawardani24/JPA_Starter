package com.dika

import java.util.*

object Logger {
    val type get() = System.loggerType

    fun printInfo(info: String) {
        when(type) {
            LoggerType.VERBOSE,
            LoggerType.FULL_VERBOSE -> println("[INFO : ${time()}] => $info")
            else -> { }
        }
    }

    fun printError(throwable: Throwable) {
        when(type) {
            LoggerType.VERBOSE,
            LoggerType.FULL_VERBOSE,
            LoggerType.VERBOSE_ERROR,
            LoggerType.FULL_VERBOSE_ERROR -> {
                java.lang.System.err.println(buildMessageError(throwable))
            }
            else -> {}
        }
    }

    private fun shouldPrintTraces(type: LoggerType): Boolean {
        return when(type) {
            LoggerType.FULL_VERBOSE,
            LoggerType.FULL_VERBOSE_ERROR -> true
            else -> false
        }
    }

    private fun buildMessageError(throwable: Throwable): String {
        val isPrintTraces = shouldPrintTraces(type)

        throwable.run {
            return StringBuilder("[ERROR : ${time()}] => $this").apply {
                if (isPrintTraces) {
                    append("\n\t[TRACES] => ${this@run}\n")
                    buildErrorTraces(throwable, this)

                    throwable.cause?.run {
                        append("\t[CAUSED_BY] => $this\n")
                        buildErrorTraces(this, this@apply)
                    }
                }
            }.toString()
        }
    }

    private fun buildErrorTraces(throwable: Throwable, builder: StringBuilder): String {
        return builder.apply {
            throwable.stackTrace.forEach {
                it.run {
                    appendln("\t\t at $className.$methodName($fileName:$lineNumber)")
                }
            }
        }.toString()
    }

    private fun time(): String {
        return Calendar.getInstance().time.toString()
    }

    /**
     * Set logging runtime type of the apllication.
     * * Set to VERBOSE if you want to printout activity infoIcon and only message stack trace on console
     * * Set to FULL_VERBOSE if you want to printout activity infoIcon and full stack trace on console
     * * Set to VERBOSE_ERROR if you want to printout only message stack trace on console
     * * Set to FULL_VERBOSE_ERROR if you want to printout only full stack trace on console
     * * Set to NONE otherwise
     */
    enum class LoggerType {
        VERBOSE, FULL_VERBOSE, VERBOSE_ERROR, FULL_VERBOSE_ERROR, NONE
    }
}