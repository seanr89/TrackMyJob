package com.example.sean.trackmyjob.Business

import android.util.Log
import com.example.sean.trackmyjob.Models.TimeDiff
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * test object for calculating stuff!!
 */
object TimeCalculator
{
    private val TAG = "TimeCalculator"

    /**
     * calculate the time difference between the two times
     * @param start : a clock in date and time
     * @param stop : a clock out date and time
     * @return TimeDiff : object that provides a separated Hours and Minutes calculation of time difference
     */
    fun difference(start: LocalDateTime, stop: LocalDateTime): TimeDiff
    {
        val diff = TimeDiff(0, 0)

        //temp object in order to append the Hours back to it so the correct amount of Minutes can be appended!
        var fromTemp = LocalDateTime.from(start)

        val hours = stop.until(fromTemp, ChronoUnit.HOURS).toInt()
        fromTemp = fromTemp.plusHours(hours.toLong())

        val minutes = stop.until(fromTemp, ChronoUnit.MINUTES).toInt()

        diff.Hours = hours
        diff.Minutes = minutes

        Log.d(TAG, "difference is $hours hrs + $minutes mins")

        return diff
    }

    /**
     * Handle the combining of two TimeDiff objects and return it
     * @param timeDiffCurrent : the current time diff to take the Hours and Minutes from
     * @param timeDiffStored : the stored timeDiff to append to!
     * @return TimeDiff with Hours and Minutes combined
     */
    fun combineTimeDiffs(timeDiffCurrent : TimeDiff, timeDiffStored : TimeDiff) : TimeDiff
    {
        val combinedTimeDiff = TimeDiff()

        combinedTimeDiff.Hours = timeDiffCurrent.Hours + timeDiffStored.Hours
        combinedTimeDiff.Minutes = timeDiffCurrent.Minutes + timeDiffStored.Minutes

        val timeCalc = HoursAndMinutes(combinedTimeDiff.Minutes)
        val calculatedHours = timeCalc.calculateHours()
        val calculatedMinutes = timeCalc.calculateRemainingMinutesFromHours()

        combinedTimeDiff.Hours = combinedTimeDiff.Hours + calculatedHours
        combinedTimeDiff.Minutes = calculatedMinutes

        return combinedTimeDiff
    }
}