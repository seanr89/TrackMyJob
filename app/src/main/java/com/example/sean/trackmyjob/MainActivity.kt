package com.example.sean.trackmyjob

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
     *
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

}
