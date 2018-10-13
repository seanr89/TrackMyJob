package com.example.sean.trackmyjob.Models

class HoursAndMinutes(var minutes : Int) {

    /**
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
     * @return integer value of remain minutes with hours calculated
     */
    fun calculateRemainingMinutesFromHours() : Int
    {
        val hours = calculateHours()
        var mins = 0
        if(hours != 0)
        {
            mins = hours * 60
            return minutes - mins
        }
        return minutes
    }
}