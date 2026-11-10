package com.fabio.callblocker.data

import kotlinx.coroutines.flow.Flow

class BlockedCallRepository(private val dao: BlockedCallDao) {
    val allCalls: Flow<List<BlockedCall>> = dao.getAll()

    suspend fun insert(number: String?, timestamp: Long = System.currentTimeMillis()): Long {
        return dao.insert(BlockedCall(number = number, timestamp = timestamp))
    }

    suspend fun deleteByIds(ids: List<Long>) {
        dao.deleteByIds(ids)
    }

    suspend fun clearAll() {
        dao.clearAll()
    }
}
