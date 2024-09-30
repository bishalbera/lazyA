package com.bishalbera.lazyA

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class ForegroundService : Service() {
    companion object {
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }

    override fun onCreate() {
            super.onCreate()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =  NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val manager = getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)
            }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("FOREGROUND_SERVICE", "Service started")

        val notification : Notification = NotificationCompat.Builder(this, CHANNEL_ID)
           .setContentTitle("App Running in Background")
           .setContentText("The app is monitoring your interactions")
           .build()
        startForeground(1,notification)

        // TODO: Your background task logic goes here
        val clickServiceIntent = Intent(this, ClickService::class.java)
        startService(clickServiceIntent)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
       return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Handle cleanup if needed
    }
}