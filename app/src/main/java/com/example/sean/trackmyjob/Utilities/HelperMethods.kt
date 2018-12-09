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
     * check if the current day denotes the start of a week!
     * @param dateTime :
     * @return Boolean
     */
    fun isDayStartOfWeek(dateTime : LocalDateTime) : Boolean
    {
        if(dateTime.dayOfWeek.value == 1)
        {
            return true
        }
        return false
    }

    /**
     * check if the current day denotes the start of a month
     * @param dateTime :
     * @return Boolean
     */
    fun isDayStartOfMonth(dateTime : LocalDateTime) : Boolean
    {
        if(dateTime.dayOfMonth == 1)
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
        if(dateTime.dayOfWeek == DayOfWeek.SATURDAY || dateTime.dayOfWeek == DayOfWeek.SUNDAY) {
            return true
        }
        return false
    }

    /**
     * handle check to see if the day, month and year of the two provided days match!!
     * @param date : date one to be used in comparison
     * @param dateTwo : the second date to compare
     * @return boolean : true or false
     */
    fun doDaysMatch(date :LocalDateTime, dateTwo : LocalDateTime) : Boolean
    {
        return (date.dayOfWeek.value == dateTwo.dayOfWeek.value
                && date.monthValue == dateTwo.monthValue
                && date.year == dateTwo.year)
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