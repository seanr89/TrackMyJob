package com.example.sean.trackmyjob.Business

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.ClockEventStats
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Models.Enums.LastClock
import com.example.sean.trackmyjob.Models.TimeDiff
import com.example.sean.trackmyjob.Repositories.ClockEventStatsRepository
import com.example.sean.trackmyjob.Utilities.HelperMethods
import com.google.firebase.crash.FirebaseCrash.log
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class ClockEventStatsManager
{
    private val TAG = "ClockEventStatsManager"

    /**
     * handles communication to the stats repo and will review and attempt to update stats accordingly
     * include weekly and monthly Hours worked and monthly stats archiving
     * @param clockEvent : the clock event to handle update events from!!
     */
    fun handleClockEventAndUpdateStatsIfRequired(clockEvent: ClockEvent, lastClockEvent : ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

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
     * @param clockEvent : the current clock event just triggered
     * @param clockEventStats : the currently stored stats for the user!
     * @param lastClockEvent : the last stored clock event!
     */
    private fun processClockEventAndCurrentStats(clockEvent: ClockEvent, clockEventStats: ClockEventStats?, lastClockEvent: ClockEvent){

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
        //check if the date of the last clock in matches the current day and is the start of a Month/Week
        val lastClock = getLastClockLastClockType(LocalDateTime.now(),lastClockEvent)

        //Log.d(TAG, object{}.javaClass.enclosingMethod?.name + "with type : $lastClock")
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
     * Handle a clock out event and process the updating of stats
     * @param clockEvent : the clock event to process and attempt to fix
     * @param clockEventStats : the current stats of clock events
     * @param lastClockEvent : the last known clock event stored!
     */
    private fun handleClockOut(clockEvent: ClockEvent, clockEventStats: ClockEventStats, lastClockEvent: ClockEvent)
    {
        if(lastClockEvent.event == ClockEventType.IN)
        {
            val diff = TimeCalculator.difference(clockEvent.dateTimeToLocalDateTime(), lastClockEvent.dateTimeToLocalDateTime())
            if(diff.Minutes >= 0)
            {
                val combinedDiffDaily = TimeCalculator.combineTimeDiffs(diff, clockEventStats.DailyTime)
                val combinedDiffWeekly = TimeCalculator.combineTimeDiffs(diff, clockEventStats.WeeklyTime)
                val combinedDiffMonthly = TimeCalculator.combineTimeDiffs(diff, clockEventStats.MonthlyTime)

                clockEventStats.DailyTime = combinedDiffDaily
                clockEventStats.WeeklyTime = combinedDiffWeekly
                clockEventStats.MonthlyTime = combinedDiffMonthly

                //now to update!!
                ClockEventStatsRepository.updateClockEventStatsSummary(clockEventStats)
            }
            else{
                log("$TAG : Minutes are negative - this is a problem!!")
            }
        }
    }

    /**
     * Handle the request to find out what type of clock this is in regards to day, Week or Month refresh!
     * @param lastClockEvent :
     * @return an enum detailing what the last clock type was in relation to the current date!!
     */
    fun getLastClockLastClockType(date : LocalDateTime,lastClockEvent: ClockEvent) : LastClock
    {
        var result: LastClock = LastClock.SAME_DAY
        //val date = LocalDateTime.now()
        if(!HelperMethods.doDaysMatch(lastClockEvent.dateTimeToLocalDateTime(), date))
        {
            result = LastClock.NEW_DAY
            if(HelperMethods.isDayStartOfMonth(date))
                result = LastClock.NEW_MONTH
            else if(HelperMethods.isDayStartOfWeek(date))
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
        clockEventStats.DailyTime = TimeDiff()

        ClockEventStatsRepository.updateClockEventStatsSummary(clockEventStats)
    }

    /**
     * handle the resetting of daily, and weekly stats
     * @param clockEventStats :
     */
    private fun resetWeeklyTime(clockEventStats: ClockEventStats)
    {
        clockEventStats.DailyTime = TimeDiff()
        clockEventStats.WeeklyTime = TimeDiff()
        clockEventStats.Week = Calendar.WEEK_OF_YEAR

        ClockEventStatsRepository.updateClockEventStatsSummary(clockEventStats)
    }

    /**
     * handle the resetting of daily, weekly and monthly stats
     * @param clockEventStats :
     */
    private fun resetMonthlyTime(clockEventStats: ClockEventStats)
    {
        clockEventStats.DailyTime = TimeDiff()
        clockEventStats.WeeklyTime = TimeDiff()
        clockEventStats.MonthlyTime = TimeDiff()

        clockEventStats.Week = Calendar.WEEK_OF_YEAR
        clockEventStats.Month = LocalDate.now().month.name

        if(LocalDate.now().year != clockEventStats.Year)
        {
            clockEventStats.Year = LocalDate.now().year
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
}