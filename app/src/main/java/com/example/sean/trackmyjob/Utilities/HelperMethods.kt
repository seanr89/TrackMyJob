package com.example.sean.trackmyjob.Utilities

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

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
     *
     */
    fun isDayStartOfWeek() : Boolean
    {
        if(LocalDate.now().dayOfWeek.value == 1)
        {
            return true
        }
        return false
    }

    /**
     *
     */
    fun isDayStartOfMonth() : Boolean
    {
        if(LocalDate.now().dayOfMonth == 1)
        {
            return true
        }
        return false
    }

    /**
     * return true if the provided dateTime is saturday or sunday!
     * @param dateTime : the dateTime to query
     * @return true if weekend else false
     */
    fun isWeekend(dateTime : LocalDateTime) : Boolean
    {
        if(dateTime.dayOfWeek <= DayOfWeek.SATURDAY || dateTime.dayOfWeek == DayOfWeek.SUNDAY) {
            return true
        }
        return false
    }

    fun doesDateMatchToday(date : LocalDateTime) : Boolean
    {
        return (date.dayOfWeek.value == LocalDateTime.now().dayOfWeek.value
                && date.monthValue == LocalDateTime.now().monthValue
                && date.year == LocalDateTime.now().year)
    }

    /**
     * method to query the current time and check if is in the morning!!
     * @param dateTime : the dateTime to query
     * @return false if after 12pm (default)
     */
    fun isMorning(dateTime : LocalDateTime) : Boolean
    {
        if (dateTime.hour >= 12) {
            return false
        }
        return true
    }
}