package com.example.sean.trackmyjob.Models

import com.example.sean.trackmyjob.Models.Enums.ClockEventType

/**
 *
 */
data class UserOverview(val events: Int,
                        val currentStatus : ClockEventType = ClockEventType.OUT,
                        val lastEvent: ClockEvent,
                        val weeklyHours : Int = 0,
                        val weeklyMinutes : Int = 0,
                        val totalHours : Int = 0,
                        val totalMinutes : Int = 0)
{
    /**
     *
     */
    fun totalHoursAndMinutesToString() : String
    {
        return ""
    }

    /**
     *
     */
    fun weeklyHoursAndMinutesToString() : String
    {
        return ""
    }
}