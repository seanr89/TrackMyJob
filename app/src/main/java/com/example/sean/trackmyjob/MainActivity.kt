package com.example.sean.trackmyjob

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sean.trackmyjob.ui.main.MainFragment

class MainActivity : AppCompatActivity(), ClockEventFragment.OnFragmentInteractionListener {

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
     *
     */
    override fun onFragmentInteraction(uri: Uri) {
    }

}
