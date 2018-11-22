package com.example.sean.trackmyjob.Business

import android.content.Context
import android.util.Log
import android.util.Log.d
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Repositories.ClockEventRepository

/**
 * new manager class to provide cental logic for clock event controls
 * to allow core functionally to be removed from activities and fragments for clock events!
 */
class ClockEventManager
/**
 * constructor
 */(con: Context?) {

    private val TAG = "ClockEventManager"
    private val context : Context? = con
    private val prefsHelper : PreferencesHelper

    init {
        prefsHelper = PreferencesHelper(context)
    }

    /**
     * save the clock event to storage and execute summary update!
     * @param clockEvent : the clock event to save!
     */
    fun saveClock(clockEvent: ClockEvent, onComplete: (Boolean) -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)
        val lastClock = prefsHelper.readLastStoredClock()
        d(TAG, "lastClock dateTime of : ${lastClock.dateTime}")

        when(clockEvent.event)
        {
            ClockEventType.IN -> clockInUser(clockEvent, lastClock)
            {
                onComplete(it)
            }
            ClockEventType.OUT -> clockOutUser(clockEvent, lastClock){
                onComplete(it)
            }
        }
    }

    /**
     * clock in the user!!
     * @param clockEvent : the current clock in event to save
     * @param lastClock : the last stored clock in event (onApp)
     */
    private fun clockInUser(clockEvent : ClockEvent, lastClock : ClockEvent, onComplete:(Boolean) -> Unit)
    {
        if(lastClock.dateTime >= 0)
        {
            if(lastClock.event == ClockEventType.OUT)
            {
                ClockEventRepository.addClockEventForUser(clockEvent)
                {
                    if(it)
                    {
                        val statsManager = ClockEventStatsManager()
                        statsManager.handleClockEventAndUpdateStatsIfRequired(clockEvent, lastClock)
                        prefsHelper.updateLastStoredClock(clockEvent)
                    }
                    onComplete(it)
                }
            }
            else{
                d(TAG, "User is already logged in!!")
                onComplete(false)
            }
        }
        else{
            d(TAG, "Unable to identify last clock!")
            onComplete(false)
        }
    }

    /**
     * clock out the user!!
     * @param clockEvent : the current clock in event to save
     * @param lastClock : the last stored clock in event (onApp)
     */
    private fun clockOutUser(clockEvent : ClockEvent, lastClock : ClockEvent, onComplete:(Boolean) -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        if(lastClock.dateTime >= 0)
        {
            if(lastClock.event == ClockEventType.IN)
            {
                ClockEventRepository.addClockEventForUser(clockEvent)
                {
                    if(it)
                    {
                        val statsManager = ClockEventStatsManager()
                        statsManager.handleClockEventAndUpdateStatsIfRequired(clockEvent, lastClock)
                        prefsHelper.updateLastStoredClock(clockEvent)
                    }
                    onComplete(it)
                }
            }
            else
            {
                d(TAG, "User is already logged out!!")
                onComplete(false)
            }
        }
        else
        {
            onComplete(false)
        }
    }
}