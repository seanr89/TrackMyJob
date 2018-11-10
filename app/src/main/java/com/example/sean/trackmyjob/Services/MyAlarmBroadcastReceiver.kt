package com.example.sean.trackmyjob.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.sean.trackmyjob.MainActivity
import java.time.LocalDateTime

class MyAlarmBroadcastReceiver : BroadcastReceiver()
{
    private val TAG = "MyAlarmBroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

//        val act = context as MainActivity
        if(isMorning())
        {
            //act.sendTestNotification("Morning")
        }
        else
        {
            //act.sendTestNotification("Evening")
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