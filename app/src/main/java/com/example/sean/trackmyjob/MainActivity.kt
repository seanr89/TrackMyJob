package com.example.sean.trackmyjob

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sean.trackmyjob.Models.ClockEvent
import com.google.firebase.auth.FirebaseAuth
import android.app.AlarmManager
import android.app.PendingIntent
import com.example.sean.trackmyjob.Services.MyAlarmBroadcastReceiver
import java.util.*


class MainActivity : AppCompatActivity(), ClockEventFragment.OnFragmentShowAllEventsListener,
    ClockEventListFragment.OnListFragmentInteractionListener, ClockEventFragment.OnFragmentShowAllHolidysListener
{
    private val TAG = "MainActivity"
    private lateinit var morningAlarm : AlarmManager
    private lateinit var eveningAlarm : AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ClockEventFragment.newInstance())
                    .commitNow()

            morningAlarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            eveningAlarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            createNotificationChannel()

            initialiseMorningAlarm()
            initialiseEveningAlarm()
        }
    }

    /**
     * handle creation of option action overflow on view (3 vertical dots)
     * with xml menu option file targeted
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_overflow, menu)
        return true
    }

    /**
     * overridden handle action overflow item selection events
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId) {
            R.id.action_signout -> {
                signOutUser()
                return true
            }
        }
        return true
    }


    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////

    /**
     * unknown - this is just a filler currently
     */
    override fun onListFragmentInteraction(item: ClockEvent?) {
    }

    /**
     * handle request to show fragment for all clock events!
     */
    override fun onShowAllClockEvents() {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var newFragment = ClockEventListFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null) //add the transaction to the back stack so the user can navigate back
                .commit()
    }

    override fun onShowAllHolidays() {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var newFragment = HolidayListFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null) //add the transaction to the back stack so the user can navigate back
                .commit()
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////

    /**
     * handle the signing out of the current user and return the previous activity
     */
    private fun signOutUser()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, GoogleSignInActivity::class.java))
    }

    /**
     * send a test notification to the app to alert the user to make sure they clock in or out!!
     */
//    fun sendTestNotification(time : String)
//    {
//        //https@ //developer.android.com/training/notify-user/build-notification
//
//        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
//        var mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_app_notification)
//                .setContentTitle("Title")
//                .setContentText("It is : $time")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        with(NotificationManagerCompat.from(this)) {
//            // notificationId is a unique int for each notification that you must define
//            notify(notificationId, mBuilder.build())
//        }
//    }

    /**
     * Initialise the notification channel for the app to allow for notifications to be displayed!
     */
    private fun createNotificationChannel()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * initialise the alarm manager for morning notification alerts
     */
    private fun initialiseMorningAlarm()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val intent = Intent(this, MyAlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Set the alarm to start at 08:45 PM
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 40)

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 1 day
        morningAlarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    /**
     * initialise the alarm manager for evening notification alerts
     */
    private fun initialiseEveningAlarm()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val intent = Intent(this, MyAlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Set the alarm to start at 5:20 PM
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 17)
        calendar.set(Calendar.MINUTE, 20)

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 1 day
        eveningAlarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    companion object {
        private val CHANNEL_ID = "0234"
        private val notificationId = 9876
    }
}
