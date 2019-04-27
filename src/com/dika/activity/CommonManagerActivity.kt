package com.dika.activity

import com.alee.laf.menu.WebMenuItem
import com.dika.activity.service.OnResumedAction
import com.dika.activity.service.OnStartedAction
import com.dika.database.AbstractEntity
import com.dika.report.DataReport
import com.dika.view.component.Button
import com.dika.view.component.Frame
import com.dika.view.component.Table
import com.dika.view.component.custom.HeaderLabel
import com.dika.view.custom.*
import com.dika.view.model.EntityTableModel


abstract class CommonManagerActivity<P: Number, M: AbstractEntity<P>>(title: String) : Activity<CommonManagerView>(),
        CommonManagerView, PagingTableViewService {
    private val commonManagerView:CommonManagerView = CommonManagerViewImpl(title)
    private lateinit var pagingTableViewAction: PagingTableViewAction
    protected lateinit var tableModel: EntityTableModel<M>
    override val addButton: Button = commonManagerView.addButton
    override val printButton: Button = commonManagerView.printButton
    override val pagingTableView: PagingTableView = commonManagerView.pagingTableView
    override val updateMenuItem: WebMenuItem = commonManagerView.updateMenuItem
    override val deleteMenuItem: WebMenuItem = commonManagerView.deleteMenuItem
    override val headerLabel: HeaderLabel = commonManagerView.headerLabel
    override val view: CommonManagerView = commonManagerView
    override val activity: Activity<*> = this

    private fun printDataTable() {
        if (tableModel.entities.isEmpty()) {
            showInfo("Tidak Ada Dalam Table")
            return
        }

        val report = onCreateDataReport()
        showReport(report)
    }

    fun refresh() {
        pagingTableViewAction.refreshPage()
    }

    protected fun getEntityOnSelectedRow(): M? {
        val selectedRow = pagingTableView.table.selectedRow

        return when {
            selectedRow >= 0 -> tableModel.entities[selectedRow]
            else -> null
        }
    }

    override fun initListener(view: CommonManagerView) {
        pagingTableView.run {
            table.apply {
                isEditable = false
                tableModel = createTableModel(this)
            }

            pagingTableViewAction = PagingTableViewAction(this@CommonManagerActivity,
                    this, 100)
        }

        printButton.addActionListener({ printDataTable() })

        addButton.addActionListener({ onAddEntity() })
        updateMenuItem.addActionListener {
            doWhenNotNull { onUpdate(it) }
        }
        deleteMenuItem.addActionListener {
            doWhenNotNull { onDelete(it) }
        }

        add(object : OnStartedAction {
            override fun invoke(activity: Activity<*>?) {
                pagingTableViewAction.toFirstPage()
            }
        })

        add(object : OnResumedAction {
            override fun invoke(activity: Activity<*>?) {
                pagingTableViewAction.refreshPage()
            }
        })
    }

    private fun doWhenNotNull(run: (entity: M)->Unit) {
        getEntityOnSelectedRow().let {
            when(it) {
                null -> showInfo("Tidak Ada Baris Terpilih")
                else -> run(it)
            }
        }
    }

    override fun getRoot(): Frame {
        return commonManagerView.root
    }

    protected abstract fun onUpdate(entity: M)
    protected abstract fun onDelete(entity: M)
    protected abstract fun onAddEntity()
    protected abstract fun createTableModel(table: Table): EntityTableModel<M>
    protected abstract fun onCreateDataReport(): DataReport

}