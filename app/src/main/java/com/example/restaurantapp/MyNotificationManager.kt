package com.example.restaurantapp


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat

class MyNotificationManager(var context: Context) {
    val notificationid = 1000
    val channelId = "2000"
    fun showNotification(id: Int, title: String, message: String, intent: Intent) {
        val bitmap =
            BitmapFactory.decodeResource(Resources.getSystem() , R.drawable.ic_launcher_background)



        val PendingIntent = PendingIntent.getActivity(
            context,
            notificationid,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val nBuilder = NotificationCompat.Builder(context,channelId)
        val notification = nBuilder.setSmallIcon(R.drawable.logo)
            .setContentIntent(PendingIntent)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT).build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                channelId,
                "firstChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.setShowBadge(true) //Number of Notifications
            channel.enableLights(true)
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(id, notification)


    }
    }
