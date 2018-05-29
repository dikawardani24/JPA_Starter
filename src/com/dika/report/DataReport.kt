package com.dika.report

import net.sf.dynamicreports.report.builder.DynamicReports
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment
import net.sf.dynamicreports.report.datasource.DRDataSource
import net.sf.dynamicreports.report.definition.datatype.DRIDataType

abstract class DataReport(reportTitle: String): Report(reportTitle) {
    /**
     * Crete new table column
     * @param   title set the title of this column
     * @param   fieldName set the name of this column, this name will be use as contract
     *          between this column and [DRDataSource] object
     * @param   dataType set the data type this column
     * @param   horizontalTextAlignment set horizontal alignment of the text inside column
     * @param   T the data type that will be use inside column
     * @return  `new column` of [TextColumnBuilder] which the type is [T]
     */
    private fun <T> createColumn(title: String, fieldName: String, dataType: DRIDataType<in T, T>,
                                 horizontalTextAlignment: HorizontalTextAlignment): TextColumnBuilder<T> =
            DynamicReports.col.column(title, fieldName, dataType).setHorizontalTextAlignment(horizontalTextAlignment)

    /**
     * Crete new table column and set text alignment to center
     * @param   title set the title of this column
     * @param   fieldName set the name of this column, this name will be use as contract
     *          between this column and [DRDataSource] object
     * @param   dataType set the data type this column
     * @param   T the data type that will be use inside column
     * @return  `new column` of [TextColumnBuilder] which the type is [T]
     */
    protected fun <T> createColumn(title: String, fieldName: String, dataType: DRIDataType<in T, T>): TextColumnBuilder<T> =
            createColumn(title, fieldName, dataType, HorizontalTextAlignment.CENTER)

    /**
     * Build the report after the columns is created an
     * the data source is assign to the column.
     * Then invoke the super function to create this report
     */
    override fun build() {
        jasperReportBuilder
                .columns(*createColumns().toTypedArray())
                .dataSource = createDataSource()
        super.build()
    }

    /**
     * Create data source that to be assign inside the created column
     * To be remember, data source are being contract by using column field name
     */
    protected abstract fun createDataSource(): DRDataSource

    /**
     * Create some columns for this report
     */
    protected abstract fun createColumns(): List<TextColumnBuilder<*>>
}