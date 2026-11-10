package com.fabio.callblocker.util

import com.fabio.callblocker.data.BlockedCall
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class GroupedCallItem(
    val number: String?,
    val count: Int,
    val latestTimestamp: Long,
    val ids: List<Long>
)

data class DayCallGroup(
    val dayLabel: String,
    val dateEpochStart: Long,
    val items: List<GroupedCallItem>
)

data class CallSummary(
    val countToday: Int,
    val countThisMonth: Int
)

object CallGrouper {

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun formatTime(timestamp: Long): String = timeFormat.format(Date(timestamp))

    fun groupCalls(
        calls: List<BlockedCall>,
        todayLabel: String = "Today",
        yesterdayLabel: String = "Yesterday"
    ): List<DayCallGroup> {
        if (calls.isEmpty()) return emptyList()

        val cal = Calendar.getInstance()
        val today = normalizeDay(cal.timeInMillis)
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = normalizeDay(cal.timeInMillis)

        // Agrupa por dia (start of day)
        val dayBuckets = LinkedHashMap<Long, MutableList<BlockedCall>>()
        for (call in calls) {
            val dayKey = normalizeDay(call.timestamp)
            dayBuckets.getOrPut(dayKey) { mutableListOf() }.add(call)
        }

        return dayBuckets.map { (dayStart, dayCalls) ->
            val label = when (dayStart) {
                today -> todayLabel
                yesterday -> yesterdayLabel
                else -> dateFormat.format(Date(dayStart))
            }

            // Agrupa por número dentro do mesmo dia
            val numberBuckets = LinkedHashMap<String?, MutableList<BlockedCall>>()
            for (call in dayCalls) {
                numberBuckets.getOrPut(call.number) { mutableListOf() }.add(call)
            }

            val items = numberBuckets.map { (num, list) ->
                GroupedCallItem(
                    number = num,
                    count = list.size,
                    latestTimestamp = list.maxOf { it.timestamp },
                    ids = list.map { it.id }
                )
            }.sortedByDescending { it.latestTimestamp }

            DayCallGroup(dayLabel = label, dateEpochStart = dayStart, items = items)
        }
    }

    fun computeSummary(calls: List<BlockedCall>): CallSummary {
        val cal = Calendar.getInstance()
        val todayStart = normalizeDay(cal.timeInMillis)

        cal.set(Calendar.DAY_OF_MONTH, 1)
        val monthStart = normalizeDay(cal.timeInMillis)

        var todayCount = 0
        var monthCount = 0
        for (call in calls) {
            if (call.timestamp >= todayStart) todayCount++
            if (call.timestamp >= monthStart) monthCount++
        }
        return CallSummary(countToday = todayCount, countThisMonth = monthCount)
    }

    private fun normalizeDay(timestamp: Long): Long {
        val c = Calendar.getInstance()
        c.timeInMillis = timestamp
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.timeInMillis
    }
}
