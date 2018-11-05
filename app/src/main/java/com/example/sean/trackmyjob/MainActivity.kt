package com.example.sean.trackmyjob

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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

class MainActivity : AppCompatActivity(), ClockEventFragment.OnFragmentShowAllEventsListener,
    ClockEventListFragment.OnListFragmentInteractionListener
{
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ClockEventFragment.newInstance())
                    .commitNow()

            createNotificationChannel()
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
            R.id.action_notification -> {
                sendTestNotification()
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
     *
     */
    override fun onShowAllClockEvents() {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var newFragment = ClockEventListFragment.newInstance()
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
     *
     */
    private fun sendTestNotification()
    {
        //https@ //developer.android.com/training/notify-user/build-notification

        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        var mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_background)
                .setContentTitle("Title")
                .setContentText("This is a test notification!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, mBuilder.build())
        }
    }

    /**
     *
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

    companion object {
        private val CHANNEL_ID = "0234"
        private val notificationId = 9876
    }
}
