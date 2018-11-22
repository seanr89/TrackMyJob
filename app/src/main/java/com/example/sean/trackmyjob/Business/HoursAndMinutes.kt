package com.example.sean.trackmyjob.Business

import android.util.Log

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
            val hours = minutes.rem(60)
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
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name + " with hours $hours and total minutes $minutes")
        var mins : Int
        if(hours > 0)
        {
            mins = hours * 60
            return minutes - mins
        }
        return minutes
    }
}