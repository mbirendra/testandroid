package com.birendra.lensdayshop.notification

import android.content.Context
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.birendra.lensdayshop.R

class NotificationSender(val context: Context, val title:String, val body:String) {
    val notificationManager = NotificationManagerCompat.from(context)
    var notificationChannels = NotificationChannels(context)
    fun createHighPriority()
    {
        notificationChannels.createNotificationChannels()
        val notification = NotificationCompat.Builder(context,notificationChannels.channel1)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(body)
            .setColor(Color.BLUE)
            .build()
        notificationManager.notify(1,notification)
    }
    fun createLowPriority()
    {
        notificationChannels.createNotificationChannels()
        val notification = NotificationCompat.Builder(context,notificationChannels.channel2)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(body)
            .setColor(Color.BLUE)
            .build()
        notificationManager.notify(2,notification)
    }
}