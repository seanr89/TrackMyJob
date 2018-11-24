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
     * @param onComplete([Boolean]) :
     */
    fun saveClock(clockEvent: ClockEvent, onComplete: (Boolean) -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)
        val lastClock = prefsHelper.readLastStoredClock()
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
     * clock in the user
     * @param clockEvent : the current clock in event to save
     * @param lastClock : the last stored clock in event (onApp)
     * @param onComplete([Boolean]) : returnable/lambda fun handling event completion!
     */
    private fun clockInUser(clockEvent : ClockEvent, lastClock : ClockEvent, onComplete:(Boolean) -> Unit)
    {
        if(lastClock.dateTime >= 0)
        {
            clockUser(clockEvent, lastClock, ClockEventType.IN)
            {
                onComplete(it)
            }
        }
        else{
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
        if(lastClock.dateTime >= 0)
        {
            clockUser(clockEvent, lastClock, ClockEventType.OUT)
            {
                onComplete(it)
            }
        }
        else
        {   //unable to id last known clock stored!!
            onComplete(false)
        }
    }

    /**
     * NEEDS TO BE DETAILED and tested!!
     * @param clockEvent :
     * @param lastClock :
     * @param type :
     * @param onComplete([Boolean]) :
     */
    private fun clockUser(clockEvent : ClockEvent, lastClock : ClockEvent, type: ClockEventType, onComplete:(Boolean) -> Unit)
    {
        if(lastClock.event != type)
        {
            ClockEventRepository.addClockEventForUser(clockEvent)
            {
                if(it)
                {
                    prefsHelper.updateLastStoredClock(clockEvent)
                    val statsManager = ClockEventStatsManager()
                    statsManager.handleClockEventAndUpdateStatsIfRequired(clockEvent, lastClock)
                }
                onComplete(it)
            }
        }
        else
        {
            //you are already clocked out!!
            onComplete(false)
        }
    }
}