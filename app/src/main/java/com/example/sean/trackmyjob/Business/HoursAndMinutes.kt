package com.example.sean.trackmyjob.Business

class HoursAndMinutes(var minutes : Int) {

    private val TAG = "HoursAndMinutes"

    /**
     * calculate the number of complete hours in the minutes provided
     * @return integer value of total number of hours in the provided minutes
     */
    fun calculateHours() : Int
    {
        if(minutes >= 60)
        {
            val hours = minutes.rem(60)
            return hours
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