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
                latLngProvider.getDeviceLatLng {
                    if(it != null)
                    {
                        if(DistanceChecker.isNearLocation(it))
                        {
                            val clockManager = ClockEventManager(context)
                            val clock = ClockEvent(ClockEventType.OUT)
                            clock.automatic = true
                            clockManager.saveClock(clock){
                                if(it)
                                {
                                    sendNotification(context,"You have been automatically clocked out", "Clock Event")
                                }
                            }
                        }
                        else
                        {
                            sendNotification(context,"Are you still in work?", "Clock Event")
                        }
                    }
                    else{sendNotification(context, "Please remember to clock out","Clock Event")}
                }

            }
        }
    }

    /**
     * send a test notification to the app to alert the user to make sure they clock in or out!!
     * @param context :
     * @param content :
     * @param title :
     */
    fun sendNotification(context: Context?, content : String, title : String)
    {
        //https@ //developer.android.com/training/notify-user/build-notification

        if(context != null) {
            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_app_notification)
                    .setContentTitle(title)
                    .setContentText(content)
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