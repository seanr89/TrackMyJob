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

        val hours = fromTemp.until(stop, ChronoUnit.HOURS)
        fromTemp = fromTemp.plusHours(hours)

        val minutes = fromTemp.until(stop, ChronoUnit.MINUTES)

        diff.hours = hours
        diff.minutes = minutes

        return diff
    }

    fun combineTimeDiffs(timeDiffCurrent : TimeDiff, timeDiffStored : TimeDiff) : TimeDiff
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var combinedTimeDiff = TimeDiff()

        combinedTimeDiff.hours = timeDiffCurrent.hours + timeDiffStored.hours
        combinedTimeDiff.minutes = timeDiffCurrent.minutes + timeDiffStored.minutes

        var timeCalc = HoursAndMinutes(combinedTimeDiff.minutes.toInt())
        val calculatedHours = timeCalc.calculateHours()
        val calculatedMinutes = timeCalc.calculateRemainingMinutesFromHours()

        combinedTimeDiff.hours + calculatedHours
        combinedTimeDiff.minutes = calculatedMinutes.toLong()

        return combinedTimeDiff
    }

//    /**
//     * check if the two provided dates are on the same day
//     * @param previous :
//     * @param current :
//     * @return Boolean :
//     */
//    fun isNewDay(previous : LocalDateTime, current : LocalDateTime) : Boolean
//    {
//        val dateOne = current.toLocalDate()
//        val dateTwo = previous.toLocalDate()
//        if (!dateOne.isEqual(dateTwo)) {
//            return false
//        }
//        return true
//    }
//
//    /**
//     * check if the two provided dates are on the same month
//     * @param previous :
//     * @param current :
//     * @return Boolean :
//     */
//    fun isNewMonth(previous : LocalDateTime, current : LocalDateTime) : Boolean
//    {
//        if (current.monthValue == previous.monthValue) {
//            return false
//        }
//        return true
//    }
//
//    /**
//     *
//     * @param previous :
//     * @param current :
//     * @return Booleab
//     */
//    fun isNewWeek(previous : LocalDateTime, current : LocalDateTime) : Boolean
//    {
//        if (previous.dayOfWeek <= DayOfWeek.SUNDAY && current.dayOfWeek == DayOfWeek.MONDAY) {
//            return true
//        }
//        return false
//    }
}