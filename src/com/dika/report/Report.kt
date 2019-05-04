package com.dika.report

import com.dika.System
import com.dika.activity.Activity
import com.dika.res.BigIconRes
import com.dika.res.SmallIconRes
import net.sf.dynamicreports.report.builder.DynamicReports
import net.sf.dynamicreports.report.builder.DynamicReports.cmp
import net.sf.dynamicreports.report.builder.DynamicReports.stl
import net.sf.dynamicreports.report.builder.MarginBuilder
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder
import net.sf.dynamicreports.report.builder.component.ComponentBuilder
import net.sf.dynamicreports.report.builder.style.StyleBuilder
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment
import net.sf.dynamicreports.report.constant.PageType
import net.sf.dynamicreports.report.constant.VerticalTextAlignment
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.view.JasperViewer
import java.awt.Color
import java.awt.Component
import java.util.*

open class Report(val reportTitle: String) {
    protected val jasperReportBuilder = DynamicReports.report()

    private fun buildTemplate(): ReportTemplateBuilder {
        return DynamicReports.template().apply {
            setPageFormat(PageType.A4)
            setLocale(Locale.ENGLISH)
            setPageMargin(buildMargin())
            setColumnStyle(buildColumnStyle())
            setColumnTitleStyle(buildColumnTitleStyle())
            highlightDetailEvenRows()
        }
    }

    private fun buildHeader(): ComponentBuilder<*, *> {
        cmp.run {
            return verticalList(
                    horizontalList(
                            image(BigIconRes.univIcon.image).setFixedDimension(30, 30),
                            verticalList(
                                    text(System.companyName).setStyle(buildBoldFont18Style()),
                                    text(System.addressCompany)
                                            .setStyle(buildFont8CenterStyle()))),
                    verticalGap(5),
                    line().setPen(stl.pen2Point()),
                    verticalGap(5))
        }
    }

    private fun buildTitle(title: String): ComponentBuilder<*, *> {
        cmp.run {
            return horizontalList().apply {
                add(buildHeader())
                newRow(10)
                add(text(title).setStyle(buildBoldFont18CenterStyle()))
                newRow()
                add(verticalGap(10))
            }
        }
    }

    private fun buildFooter(): ComponentBuilder<*, *> {
        return cmp.pageXofY()
                .setStyle(stl.style(buildBoldFontCenterStyle())
                        .setTopBorder(stl.pen1Point()))
    }

    open fun build() {
        jasperReportBuilder.apply {
            setTemplate(buildTemplate())
            title(buildTitle(reportTitle))
            pageFooter(buildFooter())
        }
    }

    fun showPreview(activity: Activity<*>): JasperViewer {
        return showPreview(activity.view.root)
    }

    fun showPreview(component: Component?): JasperViewer {
        build()
        val jasperPrint =  jasperReportBuilder.toJasperPrint()
        return buildPreviewWindow(component, jasperPrint).apply {
            isVisible = true
        }
    }

    private fun buildPreviewWindow(component: Component?, jasperPrint: JasperPrint): JasperViewer {
        return JasperViewer(jasperPrint, false, Locale.ENGLISH).apply {
            iconImage = SmallIconRes.appsIcon.image
            title = "Cetak $reportTitle"
            setLocationRelativeTo(component)
        }
    }

    private fun buildRootStyle(): StyleBuilder {
        return stl.style().setPadding(2)
    }

    protected fun buildBoldFontStyle(): StyleBuilder {
        return stl.style(buildRootStyle()).bold()
    }

    private fun buildBoldFontCenterStyle(): StyleBuilder {
        return stl.style(buildBoldFontStyle())
                .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE)
    }

    private fun buildBoldFont18Style(): StyleBuilder {
        return stl.style(buildBoldFontCenterStyle()).setFontSize(18)
    }

    private fun buildBoldFont18CenterStyle(): StyleBuilder {
        return buildBoldFont18Style().setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE)
    }

    private fun buildFont8CenterStyle(): StyleBuilder {
        return stl.style()
                .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.JUSTIFIED)
                .setFontSize(8)
    }

    private fun buildColumnStyle(): StyleBuilder {
        return stl.style(buildRootStyle()).setBackgroundColor(Color(199, 199, 199))
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
    }

    private fun buildColumnTitleStyle(): StyleBuilder {
        return stl.style(buildColumnStyle()).apply {
            setBorder(stl.penDouble())
            setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
            setBackgroundColor(Color(1, 90, 122))
            setForegroundColor(Color(255, 255, 255))
            bold()
        }
    }

    private fun buildMargin(): MarginBuilder {
        return DynamicReports.margin().apply {
            setTop(40)
            setLeft(40)
            setBottom(20)
            setRight(20)
        }
    }
}