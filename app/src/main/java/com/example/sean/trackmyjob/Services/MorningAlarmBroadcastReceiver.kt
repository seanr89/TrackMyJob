package com.example.sean.trackmyjob.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sean.trackmyjob.Business.ClockEventManager
import com.example.sean.trackmyjob.Business.DistanceChecker
import com.example.sean.trackmyjob.Business.MyLocationManager
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Utilities.HelperMethods
import com.google.firebase.analytics.FirebaseAnalytics
import java.time.LocalDateTime

class MorningAlarmBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "MorningAlarmBroadcastReceiver"
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private val CHANNEL_ID = "0235"
    private val notificationId = 9877

    override fun onReceive(context: Context, intent: Intent?) {

        var located = false
        var saved = false
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        val clockManager = ClockEventManager(context)

        if(HelperMethods.isWeekend(LocalDateTime.now())) return
        //ensure this is not triggering after 12!
        if(!HelperMethods.isMorning(LocalDateTime.now())) return

        val latLngProvider = MyLocationManager(context)
        latLngProvider.getDeviceLatLng {
            if (it != null)
            {
                if (DistanceChecker.isNearLocation(it))
                {
                    located = true
                    val clock = ClockEvent(ClockEventType.IN)
                    clock.automatic = true
                    clockManager.saveClock(clock) { clocked, lastClock ->
                        if (clocked) {
                            clockManager.triggerUpdateOfClockEventStats(clock, lastClock)

                            saved = true
                            logRecordToAnalytics(located, saved)
                            AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event",
                                    "You have been automatically clocked in!",
                                    CHANNEL_ID,
                                    notificationId)
                        }
                    }
                } else {
                    logRecordToAnalytics(located,saved)
                    AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event",
                            "Are you in work?",
                            CHANNEL_ID,
                            notificationId)
                }
            } else {
                logRecordToAnalytics(located,saved)
                AlarmBroadcastNotifier.sendClockNotification(context, "Clock Event",
                        "Please remember to clock in!",
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