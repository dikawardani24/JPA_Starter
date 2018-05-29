package com.dika.view.component.custom

import com.dika.res.SmallIconRes
import com.dika.view.component.FormattedTextField
import com.dika.view.component.ImageHolder
import java.text.DecimalFormat
import javax.swing.text.DefaultFormatterFactory
import javax.swing.text.NumberFormatter

open class CustomFormattedTextField(pattern: String, type: Type): FormattedTextField() {
    init {
        this.type = type
        formatterFactory = createNumberFormatter(pattern)
        value = 0
    }

    private fun createNumberFormatter(pattern: String): DefaultFormatterFactory {
        val decimalFormat = DecimalFormat(pattern)
        val numberFormatter = NumberFormatter(decimalFormat)
        return DefaultFormatterFactory(numberFormatter)
    }
}

open class CurrencyFormattedTextField: CustomFormattedTextField("Rp #,##0.##", Type.CURRENCY)

open class DecimalFormattedTextField: CustomFormattedTextField("#,##0.##", Type.DECIMAL)

class RequireCurrencyFormattedTextField: CurrencyFormattedTextField() {
    init {
        trailingComponent = ImageHolder(SmallIconRes.requireIcon)
    }
}

class RequireDecimalFormattedTextField: DecimalFormattedTextField() {
    init {
        trailingComponent = ImageHolder(SmallIconRes.requireIcon)
    }
}

class UneditableCurrencyFormattedTextField: CurrencyFormattedTextField() {
    init {
        isEditable = false
    }
}

class UneditableDecimalFormattedTextField: DecimalFormattedTextField() {
    init {
        isEditable = false
    }
}