package com.example.sean.trackmyjob.Models.Enums

/**
 * Allows fixed enumeration of clock events based on type!!
 */
enum class ClockEventType(val value: Int) {
    IN(1),
    OUT(2)
}

enum class EventSubType
{
    START,
    LUNCH,
    FLEXI,
    END,
    UNKNOWN
}