package com.dika

import com.alee.extended.window.PopOverLocation
import com.alee.laf.WebLookAndFeel
import com.dika.activity.Activity
import com.dika.activity.ActivityManager
import com.dika.res.BigIconRes
import com.dika.res.PlainTextRes
import com.dika.view.component.Frame
import com.dika.view.component.Panel
import com.dika.view.component.custom.AddButton
import com.dika.view.component.factory.PopOverFactory
import java.awt.Dimension
import java.io.File
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import javax.persistence.PersistenceException
import kotlin.concurrent.thread

object System {
    var companyName: String = "Copas Rtp"
    var addressCompanty: String = "Kp. Bojong Sompok Rt 07/05 No.115 Desa Tegal - Kemang, Bogor - Jawa Barat"
    var persitenceName: String? = null
    var loggerType = Logger.LoggerType.FULL_VERBOSE
    var allowMultipleInstance = false
    var firstActivity: Class<out Activity<*>>? = null
    private val lockFile = File("app.lock")
    var emf: EntityManagerFactory? = null;

    fun boot(block: System.()->Unit) {
        initDefaultUncaughtExceptionHandler()

        println(PlainTextRes.banner)
        if (!allowMultipleInstance) {
            if (!lockApp()) return
        }

        initOnQuitApp()
        initLookAndFeel()
        this.block()

        loadPersistenceUnit()
        loadFirstActivity()
    }

    private fun loadPersistenceUnit() {
        emf = persitenceName.let {
            if (it == null) throw SystemException("Persistence unit name must not be null", NullPointerException())

            it.run {

                try {
                    Persistence.createEntityManagerFactory(this)
                } catch (pe: PersistenceException) {
                    null
                }
            }
        }
    }

    private fun loadFirstActivity() {
        firstActivity.let {
            when(it) {
                null -> throw SystemException("No First Activity has been provided")
                else -> ActivityManager.load(it).start()
            }
        }
    }

    private fun initDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler {thread, throwable ->
            Logger.printError(throwable)
            if (thread.isAlive) {
                thread.interrupt()
            }
        }
    }

    private fun initOnQuitApp() {
        Runtime.getRuntime().addShutdownHook(thread(start = false) {
            lockFile.delete()
            println(PlainTextRes.endBanner)
        })
    }

    private fun initLookAndFeel() {
        WebLookAndFeel.install()
        WebLookAndFeel.setAllowLinuxTransparency(true)
    }

    private fun lockApp(): Boolean {
        if (lockFile.exists()) {
            PopOverFactory.warningPopOver("Infor", "Aplikasi Sudah Berjalan", PopOverFactory.WarningType.INFO)
                    .show(PopOverLocation.center)
            return false
        }

        lockFile.createNewFile()
        return true
    }
 }