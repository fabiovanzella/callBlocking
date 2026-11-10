package com.fabio.callblocker.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "blocked_calls",
    indices = [Index(value = ["timestamp"])]
)
data class BlockedCall(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val number: String?, // null se for número oculto/privado
    val timestamp: Long = System.currentTimeMillis()
)
