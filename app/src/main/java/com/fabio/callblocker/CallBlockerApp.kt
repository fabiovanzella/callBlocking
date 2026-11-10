package com.fabio.callblocker

import android.app.Application
import com.fabio.callblocker.data.AppDatabase
import com.fabio.callblocker.data.BlockedCallRepository
import com.fabio.callblocker.data.SettingsRepository
import com.fabio.callblocker.notify.BlockedCallNotifier

class CallBlockerApp : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var blockedCallRepository: BlockedCallRepository
        private set

    lateinit var settingsRepository: SettingsRepository
        private set

    lateinit var blockedCallNotifier: BlockedCallNotifier
        private set

    override fun onCreate() {
        super.onCreate()

        database = AppDatabase.getInstance(this)
        blockedCallRepository = BlockedCallRepository(database.blockedCallDao())
        settingsRepository = SettingsRepository(this)
        blockedCallNotifier = BlockedCallNotifier(this).apply {
            createNotificationChannel()
        }
    }
}
