package com.fabio.callblocker.util

import android.telephony.PhoneNumberUtils
import java.util.Locale

object PhoneFormatter {
    fun format(
        number: String?,
        fallbackUnknown: String = "Private number",
        defaultCountryIso: String = Locale.getDefault().country
    ): String {
        if (number.isNullOrBlank()) {
            return fallbackUnknown
        }
        val country = if (defaultCountryIso.isNotBlank()) defaultCountryIso else Locale.getDefault().country.ifBlank { "BR" }
        val formatted = PhoneNumberUtils.formatNumber(number, country)
        return formatted ?: number
    }
}
