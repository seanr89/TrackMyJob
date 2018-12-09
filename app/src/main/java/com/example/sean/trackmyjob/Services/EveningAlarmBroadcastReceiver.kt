package com.example.sean.trackmyjob.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.sean.trackmyjob.Business.ClockEventManager
import com.example.sean.trackmyjob.Business.DistanceChecker
import com.example.sean.trackmyjob.Business.MyLocationManager
import com.example.sean.trackmyjob.Business.PreferencesHelper
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Utilities.HelperMethods
import java.time.LocalDateTime

/**
 * new class to provide feature set for evening message calls
 */
class EveningAlarmBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "EveningAlarmBroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        //ensure the current day is not a weekend!
        if(!HelperMethods.isWeekend(LocalDateTime.now()))
        {
            //ensure this is not triggering before 12!!
            if(!HelperMethods.isMorning(LocalDateTime.now()))
            {

                //request the current location of the device!
                val latLngProvider = MyLocationManager(context)
                latLngProvider.getDeviceLatLng {
                    if(it != null)
                    {
                        val prefs = PreferencesHelper(context)
                        val officeLatLng = prefs.readOfficeLatLng()
                        //if we can id a location check if you are near work/the office!
                        if(DistanceChecker.isNearLocation(it, officeLatLng))
                        {
                            val clockManager = ClockEventManager(context)
                            val clock = ClockEvent(ClockEventType.OUT)
                            clock.automatic = true
                            clockManager.saveClock(clock){ clocked ->
                                if(clocked)
                                {
                                    AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event",
                                            "You have been automatically clocked out")
                                }
                            }
                        }
                        else
                        {
                            AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event","Are you still in work?")
                        }
                    }
                    else{
                        // if we cannot guarantee location lets ask the user to clock out!!
                        AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event", "Please remember to clock out")
                    }
                }

            } //this is not the morning
        }

    }
}