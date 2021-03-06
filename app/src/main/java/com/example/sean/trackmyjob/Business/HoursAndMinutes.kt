package com.example.sean.trackmyjob.Business

class HoursAndMinutes(var minutes : Int) {

    private val TAG = "HoursAndMinutes"

    /**
     * calculate the number of complete Hours in the Minutes provided
     * @return integer value of total number of Hours in the provided Minutes
     */
    fun calculateHours() : Int
    {
        if(minutes >= 60)
        {
            val hours = minutes.div(60)
            return hours
        }
        return 0
    }

    /**
     * calculate the number of remaining Minutes without Hours
     * @return integer value of remain Minutes with Hours calculated
     */
    fun calculateRemainingMinutesFromHours() : Int
    {
        val hours = calculateHours()
        var mins : Int
        if(hours > 0)
        {
            mins = hours * 60
            return minutes - mins
        }
        return minutes
    }
}