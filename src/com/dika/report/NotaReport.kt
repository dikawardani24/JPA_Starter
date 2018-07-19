package com.dika.report

import com.dika.database.AbstractEntity
import net.sf.dynamicreports.report.builder.DynamicReports
import net.sf.dynamicreports.report.builder.component.ComponentBuilder
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder
import net.sf.dynamicreports.report.constant.PageOrientation
import net.sf.dynamicreports.report.constant.PageType

abstract class NotaReport<M: AbstractEntity<*>>(reportTitle: String, protected val model: M): Report(reportTitle) {
    var pageType: PageType = PageType.A4

    override fun build() {
        super.build()
        jasperReportBuilder.setPageFormat(pageType, PageOrientation.LANDSCAPE)
                .title(createDetail(model))
    }

    /**
     * Create detail of the Nota / Bill
     */
    protected abstract fun createDetail(model: M): ComponentBuilder<*, *>

    /**
     * Add detail on nota report
     * @param   list set the [HorizontalListBuilder]
     * @param   label set the title of the attirube
     * @param   value set the detail valu of the attribute
     */
    protected fun addAttribute(list: HorizontalListBuilder, label: String, value: String) {
        list.add(DynamicReports.cmp.text(label).setFixedColumns(15).setStyle(buildBoldFontStyle()), DynamicReports.cmp.text(value)).newRow()
    }
}