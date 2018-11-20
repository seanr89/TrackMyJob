package com.example.sean.trackmyjob.Models

/**
 * object model to maintain the statistics/summary of clockevents
 * @param Week : the integer of the current work
 * @param Month : the name of the current Month
 * @param Year : the int of the current Year
 * @param DailyTime :
 * @param WeeklyTime :
 * @param MonthlyTime :
 */
data class ClockEventStats(var Week : Int = 0,
                           var Month : String = "January",
                           var Year : Int = 0,
                           var DailyTime : TimeDiff = TimeDiff(),
                           var WeeklyTime : TimeDiff = TimeDiff(),
                           var MonthlyTime : TimeDiff = TimeDiff()) {
}