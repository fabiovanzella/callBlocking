package com.fabio.callblocker.util

import org.junit.Assert.assertEquals
import org.junit.Test

class PhoneFormatterTest {

    @Test
    fun testFallbackUnknownNumber() {
        val formattedDefault = PhoneFormatter.format(null)
        assertEquals("Private number", formattedDefault)

        val formattedPt = PhoneFormatter.format(null, fallbackUnknown = "Número oculto")
        assertEquals("Número oculto", formattedPt)

        val formattedBlank = PhoneFormatter.format("   ", fallbackUnknown = "Número privado")
        assertEquals("Número privado", formattedBlank)
    }
}
