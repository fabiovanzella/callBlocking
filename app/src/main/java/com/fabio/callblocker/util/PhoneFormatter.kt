package com.fabio.callblocker.util

import android.telephony.PhoneNumberUtils
import java.util.Locale

object PhoneFormatter {
    fun format(number: String?, defaultCountryIso: String = Locale.getDefault().country): String {
        if (number.isNullOrBlank()) {
            return "Número oculto"
        }
        val country = if (defaultCountryIso.isNotBlank()) defaultCountryIso else "BR"
        val formatted = PhoneNumberUtils.formatNumber(number, country)
        return formatted ?: number
    }
}
