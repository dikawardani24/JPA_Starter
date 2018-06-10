package com.dika.activity

import com.dika.database.AbstractEntity
import com.dika.view.ManipulateView
import javax.swing.SwingUtilities

abstract class ManipulateEntityActivity<V : ManipulateView, E : AbstractEntity<*>> : InputActivity<V>() {
    var entity: E? = null
    set(value) {
        field = value
        if (value != null) {
            SwingUtilities.invokeLater {
                viewOldDataEntity(value)
            }
        }
    }

    abstract fun viewOldDataEntity(value: E)

    fun setTitle(title: String) {
        view.root.title = title
    }

    private fun save() {
        if (!validateInput()) return

        entity?.run {
            val old = this.clone()

            initData(this)
            if (old == this) {
                showInfo("Tidak ada perubahan dilakukan")
                return
            }

            updateEntity(this)
            return
        }

        val entity = createNewEntity()
        initData(entity)
        saveNewEntity(entity)
        clear()
    }

    abstract fun createNewEntity(): E
    protected abstract fun initData(entity: E)
    protected abstract fun saveNewEntity(entity: E)
    protected abstract fun updateEntity(entity: E)

    override fun initListener(view: V) {
        view.cancelButton.addActionListener { stop() }
        view.clearButton.addActionListener { clear() }
        view.saveButton.addActionListener { save() }
    }

    override fun clear() {
        super.clear()
        entity = null
    }
}