package com.example.sean.trackmyjob.Business

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Repositories.ClockEventRepository
import com.example.sean.trackmyjob.Utilities.HelperMethods
import java.time.LocalDateTime
import com.google.firebase.analytics.FirebaseAnalytics


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
    private var mFirebaseAnalytics: FirebaseAnalytics

    init {
        prefsHelper = PreferencesHelper(context)
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context as Context)
    }

    /**
     * save the clock event to storage and execute summary update!
     * @param clockEvent : the clock event to save!
     * @param onComplete([Boolean]) :
     */
    fun saveClock(clockEvent: ClockEvent, onComplete: (Boolean, ClockEvent) -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)
        ClockEventRepository.getLastClockEvent {
            //prefsHelper.readLastStoredClock()
            when(clockEvent.event)
            {
                ClockEventType.IN -> clockInUser(clockEvent, it as ClockEvent){result->
                    onComplete(result, it)
                    //triggerUpdateOfClockEventStats(clockEvent, it)
                }
                ClockEventType.OUT -> clockOutUser(clockEvent, it as ClockEvent){result->
                    onComplete(result, it)
                    //triggerUpdateOfClockEventStats(clockEvent, it)
                }
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
            clockUser(clockEvent, lastClock.event, ClockEventType.IN)
            {
                writeToAnalyticsLogForClockEventSuccess(clockEvent, object{}.javaClass.enclosingMethod?.name, it)
                onComplete(it)
            }
        }
        else
        {
            //we may have to add a param here to check if it is the first time to clock anything!
            if(prefsHelper.checkIsFirstClockForUser())
            {
                prefsHelper.updatePrefsOfFirstClock()

                lastClock.event = ClockEventType.OUT
                lastClock.dateTime = LocalDateTime.now().toEpochSecond(null) - 1000
                clockUser(clockEvent, lastClock.event, ClockEventType.IN)
                {
                    onComplete(it)
                }
            }
            else
            {
                onComplete(false)
            }
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
            clockUser(clockEvent, lastClock.event, ClockEventType.OUT)
            {
                writeToAnalyticsLogForClockEventSuccess(clockEvent, object{}.javaClass.enclosingMethod?.name, it)
                onComplete(it)
            }
        }
        else {
            //we may have to add a param here to check if it is the first time to clock anything!
            if (prefsHelper.checkIsFirstClockForUser()) {
                lastClock.event = ClockEventType.IN
                lastClock.dateTime = LocalDateTime.now().toEpochSecond(null) - 1000

                prefsHelper.updatePrefsOfFirstClock()
                clockUser(clockEvent, lastClock.event, ClockEventType.OUT)
                {
                    onComplete(it)
                }
            }
            else
            {
                onComplete(false)
            }
        }
    }

    /**
     * Method to refactor and handle the saving of the clock events!
     * REFACTOR  the name as this method does so much more!!
     * @param clockEvent : the clock event to save
     * @param lastClock : the last clock to review
     * @param type : the ClockEvent type to compare against
     * @param onComplete([Boolean]) : lambda method to handle async return commands!
     */
    private fun clockUser(clockEvent : ClockEvent, lastClockType : ClockEventType, type: ClockEventType, onComplete:(Boolean) -> Unit)
    {
        //if the last clock in type doesn't match the event passed, i.e. IN or OUT
        if(lastClockType != type)
        {
            ClockEventRepository.addClockEventForUser(clockEvent)
            {
                if(it)
                {
                    val params = Bundle()
                    params.putString("user_clocked", "yes")
                    params.putString("date", HelperMethods.convertDateTimeToString(clockEvent.dateTimeToLocalDateTime()))
                    params.putString("clock_type", clockEvent.event.toString())
                    mFirebaseAnalytics.logEvent("clockUser", params)
                }
                onComplete(it)
            }
        }
        else
        {
            onComplete(false)
        }
    }

    /**
     * write clock event message to Firebase Analytics
     * @param clockEvent :
     * @param methodName :
     * @param success :
     */
    private fun writeToAnalyticsLogForClockEventSuccess(clockEvent: ClockEvent, methodName : String?, success:Boolean)
    {
        val params = Bundle()
        params.putString("date", HelperMethods.convertDateTimeToString(clockEvent.dateTimeToLocalDateTime()))
        params.putString("clock_type", clockEvent.event.toString())
        params.putBoolean("completed", success)
        mFirebaseAnalytics.logEvent(methodName ?: "Unknown", params)
    }

    /**
     * trigger the updating of clock event statistics
     * @param clockEvent : the base ClockEvent that triggers the stats generation event
     * @param lastClock :
     */
    fun triggerUpdateOfClockEventStats(clockEvent: ClockEvent, lastClock: ClockEvent){
        //this can be moved out to a later in case this is causing the clock in issue!!
        val statsManager = ClockEventStatsManager()
        statsManager.handleClockEventAndUpdateStatsIfRequired(clockEvent, lastClock)
    }
}