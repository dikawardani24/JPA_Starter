package com.dika.view.custom

import com.alee.laf.menu.WebMenuItem
import com.dika.res.SmallIconRes
import com.dika.view.component.Button
import com.dika.view.component.Frame
import com.dika.view.component.Panel
import com.dika.view.component.custom.AddButton
import com.dika.view.component.custom.HeaderLabel
import com.dika.view.component.custom.PrintButton
import java.awt.Dimension
import javax.swing.WindowConstants

class CommonManagerViewImpl(title: String): Frame(), CommonManagerView {
    private val panel = Panel()
    private val pagingTableViewImpl = PagingTableViewImpl()
    override val addButton: Button = AddButton()
    override val printButton: Button = PrintButton()
    override val pagingTableView: PagingTableView = pagingTableViewImpl
    override val updateMenuItem: WebMenuItem = WebMenuItem("Perbaharui", SmallIconRes.updateIcon)
    override val deleteMenuItem: WebMenuItem = WebMenuItem("Hapus", SmallIconRes.cancelIcon)
    override val headerLabel: HeaderLabel = HeaderLabel()

    init {
        initComponents(title)
    }

    private fun initComponents(title: String) {
        headerLabel.text = title
        this.title = title

        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE

        val panel1Layout = javax.swing.GroupLayout(panel)
        panel.layout = panel1Layout
        panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(pagingTableViewImpl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()))
                                .addContainerGap())
        )
        panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pagingTableViewImpl, javax.swing.GroupLayout.DEFAULT_SIZE, 448, java.lang.Short.MAX_VALUE.toInt()))
        )

        val layout = javax.swing.GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                .addContainerGap())
        )
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                .addContainerGap())
        )

        pack()

        val size = Dimension(800, 500)
        minimumSize = size
        preferredSize = size

    }

    override fun getRoot(): Frame {
        return this
    }

}