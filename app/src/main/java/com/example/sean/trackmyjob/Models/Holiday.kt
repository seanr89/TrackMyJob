package com.example.sean.trackmyjob.Models

import com.example.sean.trackmyjob.Models.Enums.HolidayType
import java.time.LocalDateTime

/**
 * A object to detail an individual holiday
 * @param startDateTime : the start date and time of the holiday event
 * @param endDateTime : the end date and time of the holiday event
 * @param type : the type of holiday event to allow filtering and holiday customisation (enum)
 * @param attended : if the holiday has been attended
 */
data class Holiday(var startDateTime : LocalDateTime, var endDateTime: LocalDateTime, var type : HolidayType, var attended: Boolean = false) {
}

