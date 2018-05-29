package com.dika.activity

import com.dika.Logger
import com.dika.SystemException
import com.dika.activity.service.ActivityAction
import com.dika.activity.service.OnResumedAction
import com.dika.activity.service.OnStartedAction
import com.dika.activity.service.OnStoppedAction
import com.dika.database.DatabaseService
import com.dika.database.exception.ConnectionException
import com.dika.database.exception.UnloadPersistenceException
import com.dika.report.Report
import com.dika.view.View
import com.dika.view.component.GlassPanel
import com.dika.view.component.PopOver
import com.dika.view.component.factory.PopOverFactory
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.RootPaneContainer
import javax.swing.SwingUtilities
import kotlin.concurrent.thread
import kotlin.reflect.KClass

abstract class Activity<V : View<*>> {
    abstract val view: V
    var state = ActivityState.BEGIN
    var parent: Activity<*>? = null
    private val onStartedActions = ArrayList<OnStartedAction>()
    private val onResumedActions = ArrayList<OnResumedAction>()
    private val onStoppedActions = ArrayList<OnStoppedAction>()
    private val childs = ArrayList<Activity<*>>()

    fun start() {
        view.run {
            setup(this)
            root.setLocationRelativeTo(parent?.view?.root)
            display()
            invokeLater(onStartedActions)
        }

        parent?.let {
            whenViewIsJRootpane(it.view) {
                glassPane?.isVisible = true
            }
        }
    }

    fun stop() {
        view.close()
        invokeLater(onStoppedActions)
        parent?.let {
            whenViewIsJRootpane(it.view) {
                glassPane?.isVisible = false
            }
        }

        if (!childs.isEmpty()) {
            childs.forEach {
                it.stop()
            }
            childs.clear()
        }
    }

    private fun resume() {
        invokeLater(onResumedActions)
    }

    private fun invokeLater(activityActions: List<ActivityAction>) {
        SwingUtilities.invokeLater {
            activityActions.forEach {
                it.invoke(this)
            }
        }
    }

    private fun setup(view: V) {
        when (state) {
            ActivityState.BEGIN -> {
                view.run {
                    initListener(this)
                    initBlockPane(this)
                    initOnClose(this)
                }

                state = ActivityState.RESUME
            }

            else -> {
                resume()
            }
        }
    }

    private fun initBlockPane(view: V) {
        view.run {
            whenViewIsJRootpane(this) {
                if (glassPane == null) {
                    glassPane = GlassPanel()
                }
            }
        }
    }

    private fun initOnClose(view: V) {
        view.run {
            root.addWindowListener(object : WindowAdapter() {
                override fun windowClosed(e: WindowEvent?) {
                    super.windowClosed(e)
                    stop()
                }
            })
        }
    }

    private fun whenViewIsJRootpane(view: View<*>, block: RootPaneContainer.() -> Unit) {
        view.root.run {
            if (this is RootPaneContainer) {
                block()
            } else {
                Logger.printInfo("$javaClass is not JRootPane")
            }
        }
    }

    protected abstract fun initListener(view: V)

    protected fun add(onStartedAction: OnStartedAction) {
        onStartedActions.add(onStartedAction)
    }

    protected fun add(onResumedAction: OnResumedAction) {
        onResumedActions.add(onResumedAction)
    }

    protected fun add(stoppedAction: OnStoppedAction) {
        onStoppedActions.add(stoppedAction)
    }

    protected fun addStartedAction(action: () -> Unit) {
        add(object : OnStartedAction {
            override fun invoke(activity: Activity<*>) {
                action()
            }
        })
    }

    protected fun addResumedAction(action: () -> Unit) {
        add(object : OnResumedAction {
            override fun invoke(activity: Activity<*>) {
                action()
            }
        })
    }

    protected fun addStoppedAction(action: () -> Unit) {
        add(object : OnStoppedAction {
            override fun invoke(activity: Activity<*>) {
                action()
            }
        })
    }

    private fun <A : ActivityAction, L : ArrayList<out A>> remove(activityAction: A, activityActions: L) {
        activityActions.find { it == activityAction }.let {
            when (it) {
                null -> throw SystemException("There is no $activityAction in $javaClass")
                else -> it.run {
                    stopAction(this@Activity)
                    activityActions.remove(this)
                }
            }
        }
    }

    protected fun remove(onStartedAction: OnStartedAction) {
        remove(onStartedAction, onStoppedActions)
    }

    protected fun remove(onResumedAction: OnResumedAction) {
        remove(onResumedAction, onResumedActions)
    }

    protected fun remove(onStoppedAction: OnStoppedAction) {
        remove(onStoppedAction, onStoppedActions)
    }

    private fun <L : ArrayList<out ActivityAction>> removeAll(list: L) {
        list.stream().forEach {
            remove(it, list)
        }
    }

    protected fun removeAllStartedAction() {
        removeAll(onStartedActions)
    }

    protected fun removeAllResumedAction() {
        removeAll(onResumedActions)
    }

    protected fun removeAllStoppedAction() {
        removeAll(onStoppedActions)
    }

    protected fun removeAllAction() {
        removeAllStartedAction()
        removeAllResumedAction()
        removeAllStoppedAction()
    }

    protected fun countStartedAction(): Int {
        return onStartedActions.size
    }

    protected fun countResumedAction(): Int {
        return onResumedActions.size
    }

    protected fun countStoppedAction(): Int {
        return onStoppedActions.size
    }

    protected fun countAllActivityAction(): Int {
        return countStartedAction() + countResumedAction() + countStoppedAction()
    }

    protected fun <A : Activity<*>> startOther(activityKClass: KClass<A>): A {
        return startOther(activityKClass.java)
    }

    open protected fun <A : Activity<*>> startOther(activityClass: Class<A>): A {
        return ActivityManager.load(activityClass).apply {
            parent = this@Activity
            this@Activity.childs.add(this)
            start()
        }
    }

    protected fun <A : Activity<*>> stopThenStart(activityKClass: KClass<A>): A {
        return stopThenStart(activityKClass.java)
    }

    protected fun <A : Activity<*>> stopThenStart(activityClass: Class<A>): A {
        return ActivityManager.load(activityClass).apply {
            start()
            this@Activity.stop()
        }
    }

    open protected fun <R, S : DatabaseService<*, *>> execute(dbService: S, block: S.() -> R): R {
        Logger.printInfo("Execute $dbService")
        return try {
            dbService.block()
        } catch (ec: ConnectionException) {
            showFailed("Error", "Tidak bisa menghubungkan ke database server")
            throw ec
        } catch (en: UnloadPersistenceException) {
            showFailed("${en.message}")
            throw en
        }
    }

    protected fun <R, S : DatabaseService<*, *>> execute(dbService: S, onSucceedMessage: String, onFailedMessage: String,
                                                         block: S.() -> R) : R {
        Logger.printInfo("Execute $dbService")
        return try {
            showSucceed(onSucceedMessage)
            execute(dbService, block)
        } catch (ex: Exception) {
            showFailed(onFailedMessage)
            throw ex
        }
    }

    protected fun showSucceed(message: String) {
        showWarning("Sukses", message, PopOverFactory.WarningType.SUCCEED)
    }

    protected fun showFailed(title: String, message: String) {
        showWarning(title, message, PopOverFactory.WarningType.FAILED)
    }

    protected fun showFailed(message: String) {
        showFailed("Gagal", message)
    }

    protected fun showInfo(message: String) {
        showWarning("Informasi", message, PopOverFactory.WarningType.INFO)
    }

    protected fun showProgress(title: String, message: String): PopOver {
        return PopOverFactory.progressPopOver(title, message).apply {
            addWindowListener(object : WindowAdapter() {
                override fun windowOpened(e: WindowEvent?) {
                    whenViewIsJRootpane(view) {
                        glassPane?.isVisible = true
                    }
                }

                override fun windowClosed(e: WindowEvent?) {
                    whenViewIsJRootpane(view) {
                        glassPane?.isVisible = false
                    }
                }
            })
            show(view.root)
        }
    }

    protected fun showReport(report: Report) {
        thread {
            val progress = showProgress("Cetak Laporan", "Menyiapkan ${report.reportTitle}")

            whenViewIsJRootpane(view) {
                glassPane?.isVisible = true
            }

            report.showPreview(this).apply {
                addWindowListener(object : WindowAdapter() {
                    override fun windowClosed(e: WindowEvent?) {
                        whenViewIsJRootpane(view, {
                            glassPane?.isVisible = false
                        })
                    }
                })
            }
            progress.dispose()
        }
    }

    private fun showWarning(title: String, message: String, warningType: PopOverFactory.WarningType) {
        PopOverFactory.warningPopOver(title, message, warningType).run {
            addWindowListener(object : WindowAdapter() {
                override fun windowOpened(e: WindowEvent?) {
                    whenViewIsJRootpane(view) {
                        glassPane?.isVisible = true
                    }
                }

                override fun windowClosed(e: WindowEvent?) {
                    whenViewIsJRootpane(view) {
                        glassPane?.isVisible = false
                    }
                }
            })

            show(view.root)
        }
    }

    enum class ActivityState {
        BEGIN, RESUME
    }
}