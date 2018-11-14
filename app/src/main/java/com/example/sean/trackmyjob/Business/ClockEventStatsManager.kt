package com.example.sean.trackmyjob.Business

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.ClockEventStats
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Models.TimeDiff
import com.example.sean.trackmyjob.Repositories.ClockEventStatsRepository
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
    fun handleClockEventAndUpdateStatsIfRequired(clockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //Step 1. request the current stats
        ClockEventStatsRepository.requestCurrentUserClockStatsSummary {
            if(it != null)
            {
                // we need to parse the data then if we have stats!!
                processClockInAndCurrentStats(clockEvent, it)
            } else {
                //nothing else then needs to be done!!
                createNewClockEventStatsAndSave()
            }
        }
    }

    /**
     *
     */
    private fun processClockInAndCurrentStats(clockEvent: ClockEvent, clockEventStats: ClockEventStats?){
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //Step 1 - check if this is a clock out or in
        if(clockEvent.event == ClockEventType.IN)
        {
            handleClockIn(clockEvent)
        }
        else if(clockEvent.event == ClockEventType.OUT)
        {
            handleClockOut(clockEvent)
        }
    }

    /**
     * Operation to trigger the create a new stats object and to save it
     */
    private fun createNewClockEventStatsAndSave()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        val stats = ClockEventStats(Calendar.WEEK_OF_YEAR, LocalDate.now().month.name, LocalDate.now().year, TimeDiff(), TimeDiff())

        ClockEventStatsRepository.createStatsSummary(stats)
    }


    private fun handleClockIn(clockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }

    private fun handleClockOut(clockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }
}