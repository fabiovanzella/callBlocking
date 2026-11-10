package com.fabio.callblocker.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fabio.callblocker.CallBlockerApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationDismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == BlockedCallNotifier.ACTION_DISMISS) {
            val app = context.applicationContext as? CallBlockerApp ?: return
            CoroutineScope(Dispatchers.IO).launch {
                app.settingsRepository.resetNotificationCount()
            }
        }
    }
}
