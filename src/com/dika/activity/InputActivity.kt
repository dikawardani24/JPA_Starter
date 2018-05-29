package com.dika.activity

import com.dika.ClearAble
import com.dika.view.InputView
import com.dika.view.component.factory.PopOverFactory
import java.awt.Component

abstract class InputActivity<V: InputView<*>>: Activity<V>(), ClearAble {

    override val isEmpty: Boolean
        get() = view.isEmpty

    override fun clear() {
        view.clear()
    }

    fun showNotifOn(component: Component, message: String) {
        PopOverFactory.notificationPopOver(message)
                .show(component)
    }

    fun showEmptyNotifOn(component: Component) {
        showNotifOn(component, "${component.name} Masih kosong")
    }

    protected open fun validateInput(): Boolean {
        if (isEmpty) {
            showFailed("Data input masih kosong")
            return false
        }

        view.textComponents.forEach {
            when(it) {
                is ClearAble -> {
                    if (it.isEmpty) {
                        showEmptyNotifOn(it)
                        return false
                    }
                }

                else -> {
                    if (it.text.isEmpty()) {
                        showEmptyNotifOn(it)
                        return false
                    }
                }
            }
        }

        return true
    }
}