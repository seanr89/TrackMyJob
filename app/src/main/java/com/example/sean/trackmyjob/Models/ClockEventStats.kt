package com.example.sean.trackmyjob.Models

/**
 * object model to maintain the statistics/summary of clockevents
 * @param week : the integer of the current work
 * @param month : the name of the current month
 * @param year : the int of the current year
 * @param dailyTime :
 * @param weeklyTime :
 * @param monthlyTime :
 */
data class ClockEventStats(var week : Int,
                           var month : String,
                           var year : Int,
                           var dailyTime : TimeDiff = TimeDiff(),
                           var weeklyTime : TimeDiff = TimeDiff(),
                           var monthlyTime : TimeDiff = TimeDiff()) {
}