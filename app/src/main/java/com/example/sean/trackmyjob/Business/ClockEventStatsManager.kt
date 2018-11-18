package com.example.sean.trackmyjob.Business

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.ClockEventStats
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Models.TimeDiff
import com.example.sean.trackmyjob.Repositories.ClockEventRepository
import com.example.sean.trackmyjob.Repositories.ClockEventStatsRepository
import com.example.sean.trackmyjob.Utilities.HelperMethods
import java.sql.Time
import java.time.LocalDate
import java.util.*

class ClockEventStatsManager
{
    private val TAG = "ClockEventStatsManager"

    /**
     * handles communication to the stats repo and will review and attempt to update stats accordingly
     * include weekly and monthly hours worked and monthly stats archiving
     * @param clockEvent : the clock event to handle update events from!!
     */
    fun handleClockEventAndUpdateStatsIfRequired(clockEvent: ClockEvent, lastClockEvent : ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //Step 1. request the current stats
        ClockEventStatsRepository.requestCurrentUserClockStatsSummary {
            if(it != null)
            {
                // we need to parse the data then if we have stats!!
                processClockEventAndCurrentStats(clockEvent, it, lastClockEvent)
            } else {
                //nothing else then needs to be done!!
                createNewClockEventStatsAndSave()
            }
        }
    }

    /**
     * executes the handling of a clock in or out
     * @param clockEvent :
     * @param clockEventStats :
     * @param lastClockEvent :
     */
    private fun processClockEventAndCurrentStats(clockEvent: ClockEvent, clockEventStats: ClockEventStats?, lastClockEvent: ClockEvent){
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //Check if this is a clock out or in
        if(clockEvent.event == ClockEventType.IN)
        {
            handleClockIn(clockEvent, clockEventStats as ClockEventStats, lastClockEvent)
        }
        else if(clockEvent.event == ClockEventType.OUT)
        {
            handleClockOut(clockEvent, clockEventStats as ClockEventStats, lastClockEvent)
        }
    }

    /**
     * Operation to trigger the create a new stats object and to save it
     */
    private fun createNewClockEventStatsAndSave()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val stats = ClockEventStats(Calendar.WEEK_OF_YEAR, LocalDate.now().month.name, LocalDate.now().year)
        ClockEventStatsRepository.createStatsSummary(stats)
    }


    /**
     * handle process of a clockin event
     * @param clockEvent : the clock event to process and attempt to fix
     * @param clockEventStats : the current stats of clock events
     * @param lastClockEvent : the last known clock event stored!
     */
    private fun handleClockIn(clockEvent: ClockEvent, clockEventStats: ClockEventStats, lastClockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //check if the date of the last clock in matches the current day and is the start of a month/week
        val lastClock = getLastClockLastClockType(lastClockEvent)

        Log.d(TAG, object{}.javaClass.enclosingMethod.name + "with type : $lastClock")
        when(lastClock)
        {
            LastClock.NEW_DAY ->{
                resetDailyTime(clockEventStats)
            }
            LastClock.NEW_WEEK ->{
                resetWeeklyTime(clockEventStats)
            }
            LastClock.NEW_MONTH ->{
                resetMonthlyTime(clockEventStats)
                archiveStatsInformation()
            }
            LastClock.SAME_DAY ->{
                //Does anything need to be done!!
            }
        }
    }

    /**
     *
     * @param clockEvent :
     * @param clockEventStats :
     * @param lastClockEvent :
     */
    private fun handleClockOut(clockEvent: ClockEvent, clockEventStats: ClockEventStats, lastClockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var diff = TimeCalculator.difference(clockEvent.dateTimeToLocalDateTime(), lastClockEvent.dateTimeToLocalDateTime())
        var combinedDiffDaily = TimeCalculator.combineTimeDiffs(diff, clockEventStats.dailyTime)
        var combinedDiffWeekly = TimeCalculator.combineTimeDiffs(diff, clockEventStats.weeklyTime)
        var combinedDiffMonthly = TimeCalculator.combineTimeDiffs(diff, clockEventStats.monthlyTime)

        clockEventStats.dailyTime = combinedDiffDaily
        clockEventStats.weeklyTime = combinedDiffWeekly
        clockEventStats.monthlyTime = combinedDiffMonthly

        //now to update!!
        ClockEventStatsRepository.updateClockEventStatsSummary(clockEventStats)
    }

    /**
     * @param lastClockEvent
     * @return an enum detailing what the last clock type was in relation to the current date!!
     */
    private fun getLastClockLastClockType(lastClockEvent: ClockEvent) : LastClock
    {
        var result: LastClock = LastClock.SAME_DAY
        if(!HelperMethods.doesDateMatchToday(lastClockEvent.dateTimeToLocalDateTime()))
        {
            result = LastClock.NEW_DAY
            if(HelperMethods.isDayStartOfMonth())
                result = LastClock.NEW_MONTH
            else if(HelperMethods.isDayStartOfWeek())
            {
                result = LastClock.NEW_WEEK
            }
        }
        return  result
    }

    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * handle the resetting of daily stats
     * @param clockEventStats :
     */
    private fun resetDailyTime(clockEventStats: ClockEventStats)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        clockEventStats.dailyTime = TimeDiff()

        ClockEventStatsRepository.updateClockEventStatsSummary(clockEventStats)
    }

    /**
     * handle the resetting of daily, and weekly stats
     * @param clockEventStats :
     */
    private fun resetWeeklyTime(clockEventStats: ClockEventStats)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        clockEventStats.dailyTime = TimeDiff()
        clockEventStats.weeklyTime = TimeDiff()
        clockEventStats.week = Calendar.WEEK_OF_YEAR

        ClockEventStatsRepository.updateClockEventStatsSummary(clockEventStats)
    }

    /**
     * handle the resetting of daily, weekly and monthly stats
     * @param clockEventStats :
     */
    private fun resetMonthlyTime(clockEventStats: ClockEventStats)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        clockEventStats.dailyTime = TimeDiff()
        clockEventStats.weeklyTime = TimeDiff()
        clockEventStats.monthlyTime = TimeDiff()

        clockEventStats.week = Calendar.WEEK_OF_YEAR
        clockEventStats.month = LocalDate.now().month.name

        if(LocalDate.now().year != clockEventStats.year)
        {
            clockEventStats.year = LocalDate.now().year
        }
        ClockEventStatsRepository.updateClockEventStatsSummary(clockEventStats)
    }

    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * TODO
     */
    private fun archiveStatsInformation()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name + "TODO")
    }

    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * custom enum to handle clock events!
     */
    private enum class LastClock
    {
        NEW_DAY,
        NEW_WEEK,
        NEW_MONTH,
        SAME_DAY
    }
}