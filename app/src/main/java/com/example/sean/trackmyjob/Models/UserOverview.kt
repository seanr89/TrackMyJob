package com.example.sean.trackmyjob.Models

import com.example.sean.trackmyjob.Models.Enums.ClockEventType

/**
 * data class object detailing an overview of client stats
 * @param eventCount :
 * @param currentStatus :
 * @param lastEvent :
 * @param weeklyHours :
 * @param weeklyMinutes :
 * @param totalHours :
 * @param totalMinutes :
 */
data class UserOverview(val eventCount: Int,
                        val currentStatus : ClockEventType = ClockEventType.OUT,
                        val lastEvent: ClockEvent,
                        val weeklyHours : Int = 0,
                        val weeklyMinutes : Int = 0,
                        val totalHours : Int = 0,
                        val totalMinutes : Int = 0)
{
    /**
     * calculate and output to string the parameters
     * @return string output of hours and minutes
     */
    fun totalHoursAndMinutesToString() : String
    {
        return ""
    }

    /**
     *
     * @return string output of hours and minutes
     */
    fun weeklyHoursAndMinutesToString() : String
    {
        return ""
    }
}