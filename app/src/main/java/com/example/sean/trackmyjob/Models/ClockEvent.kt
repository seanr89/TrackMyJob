package com.example.sean.trackmyjob.Models

import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Models.Enums.EventSubType
import com.example.sean.trackmyjob.Utilities.HelperMethods
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * An individual clock event that marks if someone arrived or left a specific location or work
 * @param event : the event type
 * @param dateTime : the date and time of the clock event - in Long format(to milliseconds)
 * @param subType : the sub type of the clock in or out
 * @param automatic : defines if the clock event was automated
 */
data class ClockEvent(var event : ClockEventType,
                      var dateTime : Long = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                      var subType : EventSubType = EventSubType.UNKNOWN,
                      var automatic : Boolean = false) {

    /**
     * empty constructor used for object instantiation from dataSource
     */
    constructor() : this(ClockEventType.IN)

    /**
     * format dateTime and calculate LocalDateTime
     * @return LocalDateTime of this dateTime info
     */
    fun dateTimeToLocalDateTime() : LocalDateTime
    {
        return if(this.dateTime > 0)
        {
            HelperMethods.convertLongToLocalDateTime(this.dateTime)
        }
        else
        {
            LocalDateTime.now()
        }
    }
}