package com.example.sean.trackmyjob.Business

import android.util.Log

class HoursAndMinutes(var minutes : Int) {

    private val TAG = "HoursAndMinutes"

    /**
     * calculate the number of complete hours in the minutes provided
     * @return integer value of total number of hours in the provided minutes
     */
    fun calculateHours() : Int
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name + " mins : $minutes")
        if(minutes >= 60)
        {
            val hours = minutes.rem(60)
            //Log.d(TAG, object{}.javaClass.enclosingMethod?.name + " calc'd hours $hours")
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
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)
        val hours = calculateHours()
        var mins : Int
        if(hours != 0)
        {
            mins = hours * 60
            //Log.d(TAG, object{}.javaClass.enclosingMethod?.name + " remaining ${minutes - mins}")
            return minutes - mins
        }
        //Log.d(TAG, object{}.javaClass.enclosingMethod?.name + " remaining $minutes")
        return minutes
    }
}