package com.fabio.callblocker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    private object Keys {
        val BLOCKING_ENABLED = booleanPreferencesKey("blocking_enabled")
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val NOTIF_PENDING_COUNT = intPreferencesKey("notif_pending_count")
        val LAST_BLOCKED_NUMBER = stringPreferencesKey("last_blocked_number")
        val LAST_BLOCKED_TIME = longPreferencesKey("last_blocked_time")
    }

    val blockingEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[Keys.BLOCKING_ENABLED] ?: true
    }

    val onboardingDone: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[Keys.ONBOARDING_DONE] ?: false
    }

    val notificationState: Flow<NotificationState> = context.dataStore.data.map { preferences ->
        NotificationState(
            pendingCount = preferences[Keys.NOTIF_PENDING_COUNT] ?: 0,
            lastNumber = preferences[Keys.LAST_BLOCKED_NUMBER],
            lastTimestamp = preferences[Keys.LAST_BLOCKED_TIME] ?: 0L
        )
    }

    suspend fun setBlockingEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[Keys.BLOCKING_ENABLED] = enabled
        }
    }

    suspend fun setOnboardingDone(done: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[Keys.ONBOARDING_DONE] = done
        }
    }

    suspend fun recordBlockedForNotification(number: String?, timestamp: Long): Int {
        var newCount = 1
        context.dataStore.edit { preferences ->
            val current = preferences[Keys.NOTIF_PENDING_COUNT] ?: 0
            newCount = current + 1
            preferences[Keys.NOTIF_PENDING_COUNT] = newCount
            if (number != null) {
                preferences[Keys.LAST_BLOCKED_NUMBER] = number
            } else {
                preferences.remove(Keys.LAST_BLOCKED_NUMBER)
            }
            preferences[Keys.LAST_BLOCKED_TIME] = timestamp
        }
        return newCount
    }

    suspend fun resetNotificationCount() {
        context.dataStore.edit { preferences ->
            preferences[Keys.NOTIF_PENDING_COUNT] = 0
            preferences.remove(Keys.LAST_BLOCKED_NUMBER)
            preferences.remove(Keys.LAST_BLOCKED_TIME)
        }
    }
}

data class NotificationState(
    val pendingCount: Int,
    val lastNumber: String?,
    val lastTimestamp: Long
)
