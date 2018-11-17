package com.example.sean.trackmyjob.Services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sean.trackmyjob.Business.ClockEventManager
import com.example.sean.trackmyjob.Business.DistanceChecker
import com.example.sean.trackmyjob.Business.MyLocationManager
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.R
import com.example.sean.trackmyjob.Utilities.HelperMethods
import java.time.LocalDateTime

class MorningAlarmBroadcastReceiver : BroadcastReceiver() {
    private val TAG = "MorningAlarmBroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        if(!HelperMethods.isWeekend(LocalDateTime.now()))
        {
            if(isMorning())
            {
                val latLngProvider = MyLocationManager(context)
                if(DistanceChecker.isNearLocation(latLngProvider.getDeviceLatLng())) {

                    var clockManager = ClockEventManager(context)
                    val clock = ClockEvent(ClockEventType.IN)
                    clockManager.saveClock(clock)
                }
                else
                {
                    sendNotification(context, "In")
                }
            }
        }
    }

    /**
     * send a test notification to the app to alert the user to make sure they clock in or out!!
     * @param context :
     * @param time :
     */
    fun sendNotification(context: Context?,time : String)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name + time)
        //https@ //developer.android.com/training/notify-user/build-notification

        if(context != null) {
            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_app_notification)
                    .setContentTitle("Clock In")
                    .setContentText("Remember to Clock $time")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
        private val CHANNEL_ID = "0235"
        private val notificationId = 9877
    }
}