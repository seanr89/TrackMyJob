package com.example.sean.trackmyjob.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sean.trackmyjob.R
import java.time.LocalDateTime
import android.app.NotificationManager
import android.location.LocationManager
import com.example.sean.trackmyjob.Business.ClockEventManager
import com.example.sean.trackmyjob.Business.DistanceChecker
import com.example.sean.trackmyjob.Business.MyLocationManager
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Utilities.HelperMethods


/**
 * BoardcastReceiver for handling alarmManager event calls for clockIn and ClockOut
 */
open class MyAlarmBroadcastReceiver : BroadcastReceiver()
{
    private val TAG = "MyAlarmBroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        if(!HelperMethods.isWeekend(LocalDateTime.now()))
        {
            if(!HelperMethods.isMorning(LocalDateTime.now()))
            {
                val latLngProvider = MyLocationManager(context)
                if(DistanceChecker.isNearLocation(latLngProvider.getDeviceLatLng()))
                {
                    val clockManager = ClockEventManager(context)
                    val clock = ClockEvent(ClockEventType.OUT)
                    clockManager.saveClock(clock)
                }
                else
                {
                    sendNotification(context,"Out")
                }
            }
        }
    }

    /**
     * send a test notification to the app to alert the user to make sure they clock in or out!!
     */
    fun sendNotification(context: Context?,time : String)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //https@ //developer.android.com/training/notify-user/build-notification

        if(context != null) {
            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_app_notification)
                    .setContentTitle("Clock Out")
                    .setContentText("Remember to Clock $time")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, mBuilder.build())
        }
    }

    companion object {
        private val CHANNEL_ID = "0234"
        private val notificationId = 9876
    }
}