package com.youapps.onlybeans.utilities

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object NumericUtils {

    fun formatPrice(price: Float, locale: Locale): String {
        Currency.getInstance(locale)
        val format = NumberFormat.getCurrencyInstance(locale)
        return format.format(price)
    }
}