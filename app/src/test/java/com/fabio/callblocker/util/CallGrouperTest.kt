package com.fabio.callblocker.util

import com.fabio.callblocker.data.BlockedCall
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

class CallGrouperTest {

    @Test
    fun testGroupingAndRepetitionCount() {
        val now = System.currentTimeMillis()
        val calls = listOf(
            BlockedCall(id = 1, number = "11987654321", timestamp = now),
            BlockedCall(id = 2, number = "11987654321", timestamp = now - 1000),
            BlockedCall(id = 3, number = "11987654321", timestamp = now - 2000),
            BlockedCall(id = 4, number = "21999998888", timestamp = now - 500)
        )

        val groups = CallGrouper.groupCalls(calls)
        assertEquals(1, groups.size)
        assertEquals("Hoje", groups[0].dayLabel)
        assertEquals(2, groups[0].items.size)

        val item1 = groups[0].items.find { it.number == "11987654321" }!!
        assertEquals(3, item1.count)
        assertEquals(listOf(1L, 2L, 3L), item1.ids)

        val item2 = groups[0].items.find { it.number == "21999998888" }!!
        assertEquals(1, item2.count)
    }

    @Test
    fun testHiddenNumberGrouping() {
        val now = System.currentTimeMillis()
        val calls = listOf(
            BlockedCall(id = 1, number = null, timestamp = now),
            BlockedCall(id = 2, number = null, timestamp = now - 500)
        )

        val groups = CallGrouper.groupCalls(calls)
        assertEquals(1, groups.size)
        assertEquals(1, groups[0].items.size)
        assertEquals(2, groups[0].items[0].count)
        assertEquals(null, groups[0].items[0].number)
    }

    @Test
    fun testSummaryComputation() {
        val now = System.currentTimeMillis()
        val calls = listOf(
            BlockedCall(id = 1, number = "111", timestamp = now),
            BlockedCall(id = 2, number = "222", timestamp = now)
        )

        val summary = CallGrouper.computeSummary(calls)
        assertEquals(2, summary.countToday)
        assertEquals(2, summary.countThisMonth)
    }
}
