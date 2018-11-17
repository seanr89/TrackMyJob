package com.example.sean.trackmyjob.Business

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Repositories.ClockEventRepository

/**
 * new manager class to provide cental logic for clock event controls
 * to allow core functionally to be removed from activities and fragments for clock events!
 */
class ClockEventManager {

    private val TAG = "ClockEventManager"

    constructor()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }

    fun saveClock(clockEvent: ClockEvent)
    {

    }

    private fun ClockInUser(clockEvent : ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //contact firebase and updated
        ClockEventRepository.addClockOutForUser(clockEvent)
    }

    private fun ClockOutUser(clockEvent : ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //contact firebase and updated
        ClockEventRepository.addClockOutForUser(clockEvent)
    }
}