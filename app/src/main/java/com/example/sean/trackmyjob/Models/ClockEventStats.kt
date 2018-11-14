package com.example.sean.trackmyjob.Models

/**
 * object model to maintain the statistics/summary of clockevents
 * @param week :
 * @param month :
 * @param year :
 * @param weeklyTime :
 * @param monthlyTime :
 */
data class ClockEventStats(var week : Int,
                           var month : String,
                           var year : Int,
                           var weeklyTime : TimeDiff,
                           var monthlyTime : TimeDiff) {
}