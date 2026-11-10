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
        assertEquals("Today", groups[0].dayLabel)
        assertEquals(2, groups[0].items.size)

        val groupsPt = CallGrouper.groupCalls(calls, todayLabel = "Hoje", yesterdayLabel = "Ontem")
        assertEquals(1, groupsPt.size)
        assertEquals("Hoje", groupsPt[0].dayLabel)

        val groupsJa = CallGrouper.groupCalls(calls, todayLabel = "今日", yesterdayLabel = "昨日")
        assertEquals(1, groupsJa.size)
        assertEquals("今日", groupsJa[0].dayLabel)

        val item1 = groups[0].items.find { it.number == "11987654321" }!!
        assertEquals(3, item1.count)
        assertEquals(listOf(1L, 2L, 3L), item1.ids)

        val item2 = groups[0].items.find { it.number == "21999998888" }!!
        assertEquals(1, item2.count)
    }

    @Test
    fun testYesterdayGroupingWithLocalizedLabels() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterdayTimestamp = cal.timeInMillis

        val calls = listOf(
            BlockedCall(id = 1, number = "11987654321", timestamp = yesterdayTimestamp)
        )

        val groupsEn = CallGrouper.groupCalls(calls)
        assertEquals("Yesterday", groupsEn[0].dayLabel)

        val groupsPt = CallGrouper.groupCalls(calls, todayLabel = "Hoje", yesterdayLabel = "Ontem")
        assertEquals("Ontem", groupsPt[0].dayLabel)

        val groupsEs = CallGrouper.groupCalls(calls, todayLabel = "Hoy", yesterdayLabel = "Ayer")
        assertEquals("Ayer", groupsEs[0].dayLabel)
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
