package com.example.sean.trackmyjob.Business

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.provider.Settings.Global.getString
import android.util.Log
import com.example.sean.trackmyjob.MainActivity
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.R
import com.example.sean.trackmyjob.R.string.pref_clockevent_date_key
import com.example.sean.trackmyjob.R.string.pref_clockevent_event_key

class PreferencesHelper
{
    private lateinit var prefs : SharedPreferences
    private val TAG = "PreferencesHelper"

    constructor(context: Context?)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     *
     * @return ClockEvent
     */
    fun readLastStoredClock() : ClockEvent
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        val clock = ClockEvent()

        val output = ClockEventType.fromInt(prefs.getInt(pref_clockevent_event_key, ClockEventType.IN.value))
        clock.event = output ?: ClockEventType.IN
        clock.dateTime = prefs.getLong(pref_clockevent_date_key, 0)

        return clock
    }

    /**
     * @param clockEvent :
     */
    fun updateLastStoredClock(clockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        val editor = prefs.edit()
        editor.putInt(pref_clockevent_event_key, clockEvent.event.value)
        editor.putLong(pref_clockevent_date_key, clockEvent.dateTime)
        editor.apply()
    }

     companion object {
         private var mySharedPrefsEvents = "myClockEventsPrefs"

         private const val pref_clockevent_event_key = "STOREDEVENTKEY"
         private const val pref_clockevent_date_key = "STOREDEVENTDATE"

     }
}