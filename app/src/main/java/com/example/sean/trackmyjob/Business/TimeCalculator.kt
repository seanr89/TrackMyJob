package com.example.sean.trackmyjob.Business

import com.example.sean.trackmyjob.Models.MyTime
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit



object TimeCalculator
{
    /**
     * calculate the time difference between the two times
     * @param start : a clock in date and time
     * @param stop : a clock out date and time
     * @return MyTime :
     */
    fun difference(start: LocalDateTime, stop: LocalDateTime): MyTime
    {
        val diff = MyTime(0, 0)

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