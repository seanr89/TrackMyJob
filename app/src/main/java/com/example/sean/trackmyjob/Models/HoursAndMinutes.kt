package com.example.sean.trackmyjob.Models

class HoursAndMinutes(var minutes : Int) {

    /**
     * calculate the number of complete hours in the minutes provided
     * @return integer value of total number of hours in the provided minutes
     */
    fun calculateHours() : Int
    {
        if(minutes != 0)
        {
            return minutes.rem(60)
        }
        return 0
    }

    /**
     * calculate the number of remaining minutes without hours
     * @return integer value of remain minutes with hours calculated
     */
    fun calculateRemainingMinutesFromHours() : Int
    {
        val hours = calculateHours()
        var mins : Int
        if(hours != 0)
        {
            mins = hours * 60
            return minutes - mins
        }
        return minutes
    }
}