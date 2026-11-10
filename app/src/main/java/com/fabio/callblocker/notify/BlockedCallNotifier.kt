package com.fabio.callblocker.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fabio.callblocker.R
import com.fabio.callblocker.ui.MainActivity
import com.fabio.callblocker.util.CallGrouper
import com.fabio.callblocker.util.PhoneFormatter

class BlockedCallNotifier(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "blocked_calls"
        const val NOTIFICATION_ID = 1001
        const val ACTION_DISMISS = "com.fabio.callblocker.ACTION_DISMISS_NOTIF"
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notif_channel_name)
            val descriptionText = context.getString(R.string.notif_channel_desc)
            val importance = NotificationManager.IMPORTANCE_LOW // Sem som, sem pop-up
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableVibration(false)
                enableLights(false)
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showBlockedNotification(count: Int, lastNumber: String?, lastTimestamp: Long) {
        val manager = NotificationManagerCompat.from(context)
        if (!manager.areNotificationsEnabled()) {
            return
        }

        val contentIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val deleteIntent = Intent(context, NotificationDismissReceiver::class.java).apply {
            action = ACTION_DISMISS
        }
        val deletePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            deleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = if (count <= 1) {
            context.getString(R.string.notif_title_single)
        } else {
            context.getString(R.string.notif_title_multiple, count)
        }

        val formattedNumber = PhoneFormatter.format(lastNumber)
        val formattedTime = CallGrouper.formatTime(lastTimestamp)
        val contentText = context.getString(R.string.notif_text_last, formattedNumber, formattedTime)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .setDeleteIntent(deletePendingIntent)
            .setOnlyAlertOnce(true)
            .build()

        try {
            manager.notify(NOTIFICATION_ID, notification)
        } catch (_: SecurityException) {
            // Em caso de falta de permissão em tempo de execução
        }
    }

    fun cancelNotification() {
        val manager = NotificationManagerCompat.from(context)
        manager.cancel(NOTIFICATION_ID)
    }
}
