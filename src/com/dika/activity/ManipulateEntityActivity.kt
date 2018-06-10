package com.dika.activity

import com.dika.database.AbstractEntity
import com.dika.view.ManipulateView

abstract class ManipulateEntityActivity<V : ManipulateView, E : AbstractEntity<*>> : InputActivity<V>() {
    var entity: E? = null

    private fun save() {
        if (!validateInput()) return

        entity?.run {
            initData(this)
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