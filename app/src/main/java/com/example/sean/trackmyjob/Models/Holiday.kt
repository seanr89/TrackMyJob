package com.example.sean.trackmyjob.Models

import com.example.sean.trackmyjob.Models.Enums.DurationType
import com.example.sean.trackmyjob.Models.Enums.HolidayType
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * A object to detail an individual holiday
 * @param startDateTime : the start date and time of the holiday event
 * @param endDateTime : the end date and time of the holiday event
 * @param type : the type of holiday event to allow filtering and holiday customisation (enum)
 * @param attended : if the holiday has been attended
 */
data class Holiday(var startDateTime : Long = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                   var endDateTime: Long = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                   var type : HolidayType,
                   var attended: Boolean = false) {
}


data class DetailedHoliday(var type : HolidayType = HolidayType.UNKNOWN,
                           var dates : List<Long>,
                           var duration : Double,
                           var durationType : DurationType)
{

}

