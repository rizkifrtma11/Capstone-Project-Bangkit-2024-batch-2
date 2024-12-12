package com.example.rasanusa.ui.location

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.rasanusa.R

class MyLocationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: "Notification"
        val message = intent?.getStringExtra("message") ?: "You have a new message!"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("GEOFENCE", "Geofence Notifications", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Geofence notification channel"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "GEOFENCE")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_rasanusa_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }
}