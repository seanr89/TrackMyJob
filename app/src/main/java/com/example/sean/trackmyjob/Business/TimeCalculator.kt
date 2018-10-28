package com.example.sean.trackmyjob.Business

import com.example.sean.trackmyjob.Models.TimeDiff
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * test object for calculating stuff!!
 */
object TimeCalculator
{
    /**
     * calculate the time difference between the two times
     * @param start : a clock in date and time
     * @param stop : a clock out date and time
     * @return TimeDiff : object that provides a separated hours and minutes calculation of time difference
     */
    fun difference(start: LocalDateTime, stop: LocalDateTime): TimeDiff
    {
        val diff = TimeDiff(0, 0)

        //temp object in order to append the hours back to it so the correct amount of minutes can be appended!
        var fromTemp = LocalDateTime.from(start)

        val hours = fromTemp.until(stop, ChronoUnit.HOURS)
        fromTemp = fromTemp.plusHours(hours)
        val minutes = fromTemp.until(stop, ChronoUnit.MINUTES)

        diff.hours = hours
        diff.minutes = minutes

        return diff
    }
}