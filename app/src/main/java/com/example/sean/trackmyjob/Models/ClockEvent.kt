package com.example.sean.trackmyjob.Models

import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Models.Enums.EventSubType
import java.time.LocalDateTime

/**
 * An individual clock event that marks if someone arrived or left a specific location or work
 * @param userID : the unique id of the user
 * @param dateTime : the date and time of the clock event
 * @param event : the event type
 * @param subType : the sub type of the clock in or out
 * @param automatic : defines if the clock event was automated
 */
data class ClockEvent( var userID : String,
                       var dateTime : LocalDateTime = LocalDateTime.now(),
                       var event : ClockEventType,
                       var subType : EventSubType,
                       var automatic : Boolean = false) {
}