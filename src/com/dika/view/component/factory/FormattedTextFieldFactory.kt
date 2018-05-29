package com.dika.view.component.factory

import com.dika.Factory
import com.dika.view.component.FormattedTextField
import java.text.DecimalFormat
import javax.swing.text.DefaultFormatterFactory
import javax.swing.text.NumberFormatter

object FormattedTextFieldFactory: Factory<FormattedTextField>() {
    override fun create(): FormattedTextField {
        return FormattedTextField()
    }

    val currencyField get() = create().apply {
        type = FormattedTextField.Type.CURRENCY
        formatterFactory = createNumberFormatter("Rp #,##0.##")
        value = 0
    }

    val decimalField get() = create().apply {
        type = FormattedTextField.Type.DECIMAL
        formatterFactory = createNumberFormatter("#,##0.##")
        value = 0
    }

    private fun createNumberFormatter(pattern: String): DefaultFormatterFactory {
        val decimalFormat = DecimalFormat(pattern)
        val numberFormatter = NumberFormatter(decimalFormat)
        return DefaultFormatterFactory(numberFormatter)
    }
}