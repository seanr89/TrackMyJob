package com.example.sean.trackmyjob.Services

import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.sean.trackmyjob.R
import android.app.NotificationManager


/**
 * Notifier for handling alarmManager event calls for clockIn and ClockOut to the UI
 */
object AlarmBroadcastNotifier
{
    private val TAG = "AlarmBroadcastNotifier"

    //private val CHANNEL_ID = "0234"
    //private val notificationId = 9876

    /**
     * send a notification to the app to alert the user to make sure they clock in or out!!
     * @param context : the parent context used in order to contact the activity and send the notification
     * @param title :
     * @param message :
     */
    fun sendClockNotification(context: Context?, title : String, message :String, channelID :String, notificationID:Int)
    {
        //https@ //developer.android.com/training/notify-user/build-notification
        if(context != null) {
            val mBuilder = NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.ic_app_notification)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationID, mBuilder.build())
        }
    }


}