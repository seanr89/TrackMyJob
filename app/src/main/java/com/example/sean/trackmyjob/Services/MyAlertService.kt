package com.example.sean.trackmyjob.Services

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent


/**
 * Service to handle callbacks from the JobScheduler. Requests scheduled with the JobScheduler
 * ultimately land on this service's "onStartJob" method. It runs jobs for a specific amount of time
 * and finishes them. It keeps the activity updated with changes via a Messenger.
 */
class MyAlertService : JobService()
{
    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        //activityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY)
        return Service.START_NOT_STICKY
    }
    override fun onStartJob(params: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        private val TAG = "MyAlertService"
    }
}