package com.example.sean.trackmyjob.Utilities

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object HelperMethods {

    fun convertLongToLocalDateTime(dateMilliseconds : Long) : LocalDateTime
    {
        return Instant.ofEpochMilli(dateMilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    /**
     * converts a local date time param to human readable string format
     * @param date : the date to convert
     * @return string in format dd/MM/yyyy HH:mm
     */
    fun convertDateTimeToString(date: LocalDateTime): String
    {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
    }
}