package com.example.sean.trackmyjob.Models

import com.example.sean.trackmyjob.Models.Enums.HolidayStatus
import com.example.sean.trackmyjob.Models.Enums.HolidayType
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * A object to detail an individual holiday
 * @param startDateTime : the start date and time of the holiday event
 * @param hours : the Hours duration fo the holiday (8 for day, 4 for half day by default)
 * @param mins : the Minutes duration of a holiday (i.e. flexi)
 * @param type : the type of holiday event to allow filtering and holiday customisation (enum)
 * @param status : if the holiday has been attended, in the future or cancelled
 */
data class Holiday(var startDateTime : Long = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                   var hours : Int = 0,
                   var mins : Int = 0,
                   var type : HolidayType,
                   var status : HolidayStatus = HolidayStatus.FUTURE) {
}

