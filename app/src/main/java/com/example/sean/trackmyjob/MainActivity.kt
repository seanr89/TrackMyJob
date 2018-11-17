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
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sean.trackmyjob.Services.MorningAlarmBroadcastReceiver
import com.example.sean.trackmyjob.Services.MyAlarmBroadcastReceiver
import java.util.*
import android.content.SharedPreferences




class MainActivity : AppCompatActivity(), ClockEventFragment.OnFragmentShowAllEventsListener,
    ClockEventListFragment.OnListFragmentInteractionListener, ClockEventFragment.OnFragmentShowAllHolidaysListener
{
    private val TAG = "MainActivity"
    //var preferences: SharedPreferences? = null

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

            createNotificationChannel(CHANNEL_ID_MORN)
            createNotificationChannel(CHANNEL_ID)

            initialiseMorningAlarm()
            initialiseEveningAlarm()

            //request the use give permission to use locations!!
            getLocationPermission()
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

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////


    /**
     * Initialise the notification channel for the app to allow for notifications to be displayed!
     */
    private fun createNotificationChannel(chanelID : String)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //https://gist.github.com/BrandonSmith/6679223

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(chanelID, name, importance).apply {
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

        val intent = Intent(this, MorningAlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Set the alarm to start at 08:40 PM
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

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////

    /**
     * Check and prompt user to give access to location
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //mMap.isMyLocationEnabled = true
            //mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    /**
     * Overridden
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED)
                {
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////


    companion object {
        private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private val CHANNEL_ID = "0234"
        private val CHANNEL_ID_MORN = "0235"
    }
}
