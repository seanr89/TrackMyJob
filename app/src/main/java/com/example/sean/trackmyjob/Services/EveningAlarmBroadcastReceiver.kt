package com.example.sean.trackmyjob.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.sean.trackmyjob.Business.ClockEventManager
import com.example.sean.trackmyjob.Business.DistanceChecker
import com.example.sean.trackmyjob.Business.MyLocationManager
import com.example.sean.trackmyjob.Business.PreferencesHelper
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Utilities.HelperMethods
import com.google.firebase.analytics.FirebaseAnalytics
import java.time.LocalDateTime

/**
 * new class to provide feature set for evening message calls
 */
class EveningAlarmBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "EveningAlarmBroadcastReceiver"
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private val CHANNEL_ID = "0234"
    private val notificationId = 9876

    override fun onReceive(context: Context?, intent: Intent?) {

        var located = false
        var saved = false

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(context as Context)

        if(HelperMethods.isWeekend(LocalDateTime.now())) return

        //ensure this is not triggering before 12!!
        if(HelperMethods.isMorning(LocalDateTime.now())) return

            //request the current location of the device!
            val latLngProvider = MyLocationManager(context)
            latLngProvider.getDeviceLatLng {
                if(it != null) {
                    //if we can id a location check if you are near work/the office!
                    if(DistanceChecker.isNearLocation(it)) {
                        located = true

                        val clockManager = ClockEventManager(context)
                        val clock = ClockEvent(ClockEventType.OUT)
                        clock.automatic = true
                        clockManager.saveClock(clock){ clocked, lastClock ->
                            if(clocked)
                            {
                                saved = true
                                logRecordToAnalytics(located,saved)
                                AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event",
                                        "You have been automatically clocked out",
                                        CHANNEL_ID,
                                        notificationId)
                                clockManager.triggerUpdateOfClockEventStats(clock, lastClock)
                            }
                        }
                    }
                    else {
                        logRecordToAnalytics(located,saved)
                        AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event",
                                "Are you still in work?",
                                CHANNEL_ID,
                                notificationId)
                    }
                }
                else{
                    logRecordToAnalytics(located,saved)
                    // if we cannot guarantee location lets ask the user to clock out!!
                    AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event",
                            "Please remember to clock out",
                            CHANNEL_ID,
                            notificationId)
                }
            }
    }

    /**
     *
     * @param located :
     * @param saved :
     */
    private fun logRecordToAnalytics(located : Boolean, saved : Boolean)
    {
        val params = Bundle()
        params.putBoolean("located", located)
        params.putBoolean("save", saved)
        firebaseAnalytics.logEvent(TAG, params)
    }
}