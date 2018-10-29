package com.example.sean.trackmyjob.Models

import java.time.LocalDate
import java.util.*

/**
 * custom object to hold user holiday stats for a calendar year
 * @param year :
 * @param totalHolidayCount :
 * @param publicHolidayCount :
 * @param takenHolidayCount :
 */
data class HolidayStats(val year:Int = LocalDate.now().year,
                        var totalHolidayCount:Int = 0,
                        var publicHolidayCount:Int = 0,
                        var takenHolidayCount:Int = 0) {
}