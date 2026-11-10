package com.fabio.callblocker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockedCallDao {
    @Query("SELECT * FROM blocked_calls ORDER BY timestamp DESC")
    fun getAll(): Flow<List<BlockedCall>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(call: BlockedCall): Long

    @Query("DELETE FROM blocked_calls WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Long>)

    @Query("DELETE FROM blocked_calls")
    suspend fun clearAll()
}
