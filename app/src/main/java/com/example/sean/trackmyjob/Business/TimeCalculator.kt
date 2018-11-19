package com.example.sean.trackmyjob.Business

import android.util.Log
import com.example.sean.trackmyjob.Models.TimeDiff
import com.example.sean.trackmyjob.Utilities.HelperMethods
import java.sql.Time
import java.time.DayOfWeek
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
     * @return TimeDiff : object that provides a separated hours and minutes calculation of time difference
     */
    fun difference(start: LocalDateTime, stop: LocalDateTime): TimeDiff
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        val diff = TimeDiff(0, 0)

        //temp object in order to append the hours back to it so the correct amount of minutes can be appended!
        var fromTemp = LocalDateTime.from(start)

        val hours = stop.until(fromTemp, ChronoUnit.HOURS).toInt()
        fromTemp = fromTemp.plusHours(hours.toLong())

        val minutes = stop.until(fromTemp, ChronoUnit.MINUTES).toInt()

        diff.hours = hours
        diff.minutes = minutes

        Log.d(TAG, "difference is $hours hrs + $minutes mins")

        return diff
    }

    /**
     * Handle the combining of two TimeDiff objects and return it
     * @param timeDiffCurrent : the current time diff to take the hours and minutes from
     * @param timeDiffStored : the stored timeDiff to append to!
     * @return TimeDiff with hours and minutes combined
     */
    fun combineTimeDiffs(timeDiffCurrent : TimeDiff, timeDiffStored : TimeDiff) : TimeDiff
    {
        val combinedTimeDiff = TimeDiff()

        combinedTimeDiff.hours = timeDiffCurrent.hours + timeDiffStored.hours
        combinedTimeDiff.minutes = timeDiffCurrent.minutes + timeDiffStored.minutes

        val timeCalc = HoursAndMinutes(combinedTimeDiff.minutes.toInt())
        val calculatedHours = timeCalc.calculateHours()
        val calculatedMinutes = timeCalc.calculateRemainingMinutesFromHours()

        combinedTimeDiff.hours + calculatedHours
        combinedTimeDiff.minutes = calculatedMinutes

        return combinedTimeDiff
    }
}