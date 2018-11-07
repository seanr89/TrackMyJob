package com.example.sean.trackmyjob.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.time.LocalDateTime

class MyAlarmBroadcastReceiver : BroadcastReceiver()
{
    private val TAG = "MyAlarmBroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if(isMorning())
        {

        }
        else
        {

        }
    }

    /**
     * method to query the current time and check if is in the morning!!
     * @return false if after 12pm (default)
     */
    private fun isMorning() : Boolean
    {
        if (LocalDateTime.now().hour >= 12) {
            return false
        }
        return true
    }
}