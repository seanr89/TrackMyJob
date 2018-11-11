package com.example.sean.trackmyjob.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sean.trackmyjob.R
import java.time.LocalDateTime
import android.app.NotificationManager


/**
 * BoardcastReceiver for handling alarmManager event calls for clockIn and ClockOut
 */
class MyAlarmBroadcastReceiver : BroadcastReceiver()
{
    private val TAG = "MyAlarmBroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        if(isMorning())
        {
            sendNotification(context,"In")
        }
        else
        {
            sendNotification(context,"Out")
        }
    }

    /**
     * send a test notification to the app to alert the user to make sure they clock in or out!!
     */
    fun sendNotification(context: Context?,time : String)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //https@ //developer.android.com/training/notify-user/build-notification

        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        if(context != null) {
            var mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_app_notification)
                    .setContentTitle("Clock Events")
                    .setContentText("Remember to Clock $time")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, mBuilder.build())
        }
    }

    /**
     * method to query the current time and check if is in the morning!!
     * @return false if after 12pm (default)
     */
    private fun isMorning() : Boolean
    {
        if (LocalDateTime.now().hour >= 12) {
            return false
        }
        return true
    }

    companion object {
        private val CHANNEL_ID = "0234"
        private val notificationId = 9876
    }
}