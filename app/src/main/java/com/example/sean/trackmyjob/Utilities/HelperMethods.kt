package com.example.sean.trackmyjob.Utilities

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object HelperMethods {

    /**
     * convert a datetime in utc from millisecond format to LocalDateTime
     * @param dateMilliseconds : utc datetime in milliseconds
     * @return LocalDateTime
     */
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

    /**
     * format UTC EpocMillisecond dateTime to LocalDateTime
     * @param longDate : the date to be converted to a LocalDateTime
     * @return LocalDateTime of this dateTime info
     */
    fun dateTimeToLocalDateTime(longDate : Long) : LocalDateTime
    {
        return if(longDate > 0)
        {
            HelperMethods.convertLongToLocalDateTime(longDate)
        }
        else
        {
            LocalDateTime.now()
        }
    }
}