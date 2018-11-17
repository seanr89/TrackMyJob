package com.example.sean.trackmyjob.Business

import android.content.Context
import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Repositories.ClockEventRepository

/**
 * new manager class to provide cental logic for clock event controls
 * to allow core functionally to be removed from activities and fragments for clock events!
 */
class ClockEventManager {

    private val TAG = "ClockEventManager"
    private val context : Context?
    private val prefsHelper : PreferencesHelper

    constructor(con: Context?)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        context = con
        prefsHelper = PreferencesHelper(context)
    }

    /**
     *
     */
    fun saveClock(clockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)


        val lastClock = prefsHelper.readLastStoredClock()
        when(clockEvent.event)
        {
            ClockEventType.IN -> clockInUser(clockEvent, lastClock)
            ClockEventType.OUT -> clockOutUser(clockEvent, lastClock)
        }

    }

    /**
     *
     */
    private fun clockInUser(clockEvent : ClockEvent, lastClock : ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        if(lastClock != null)
        {
            if(lastClock.event == ClockEventType.OUT)
            {
                ClockEventRepository.addClockInForUser(clockEvent)

                val statsManager = ClockEventStatsManager()
                statsManager.handleClockEventAndUpdateStatsIfRequired(clockEvent, lastClock)

                prefsHelper.updateLastStoredClock(clockEvent)
            }
        }
        else
        {
            //contact firebase and updated
            ClockEventRepository.addClockOutForUser(clockEvent)
        }
    }

    /**
     *
     */
    private fun clockOutUser(clockEvent : ClockEvent, lastClock : ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        if(lastClock != null)
        {
            if(lastClock.event == ClockEventType.IN)
            {
                ClockEventRepository.addClockInForUser(clockEvent)

                val statsManager = ClockEventStatsManager()
                statsManager.handleClockEventAndUpdateStatsIfRequired(clockEvent, lastClock)

                prefsHelper.updateLastStoredClock(clockEvent)
            }
        }
        else
        {
            //contact firebase and updated
            ClockEventRepository.addClockOutForUser(clockEvent)
        }
    }
}