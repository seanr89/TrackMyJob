package com.example.sean.trackmyjob

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sean.trackmyjob.Business.TimeCalculator
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Repositories.ClockEventRepository
import com.example.sean.trackmyjob.Utilities.HelperMethods
import org.w3c.dom.Text
import java.time.Clock

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ClockEventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ClockEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ClockEventFragment : Fragment(), View.OnClickListener {

    private val TAG = "ClockEventFragment"
    private var listener: OnFragmentShowAllEventsListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_clock_event, container, false)

        view.findViewById<Button>(R.id.btn_ClockIn).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_ClockOut).setOnClickListener(this)

        setLastKnownClockEventOnUI()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentShowAllEventsListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_ClockIn -> onClockIn()
            R.id.btn_ClockOut -> onClockOut()
            R.id.btn_ClockList -> onViewClockEvents()
        }// ...
    }

    /**
     * request the last known clock event that was stored for the user and display the data
     */
    private fun setLastKnownClockEventOnUI()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        ClockEventRepository.getLastClockEvent{
            if(it != null)
            {
                updateClockEventInfo(it)
            }
        }
    }

    /**
     * add a clock in event for the current user
     */
    private fun onClockIn()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        val currentClockEvent = readSharedPreferencesForLastClock()
        if(currentClockEvent.event == ClockEventType.OUT)
        {
            val clock = ClockEvent(ClockEventType.IN)
            ClockEventRepository.addClockInForUser(clock)
            updateSharedPreferencesOfLastClock(clock)
            updateClockEventInfo(clock)
        }
        else
        {
            Toast.makeText(context, "You are already Clocked In!", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * add a clock out event for the current user
     */
    private fun onClockOut()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        val lastClock = readSharedPreferencesForLastClock()
        if(lastClock.event == ClockEventType.IN)
        {
            val clock = ClockEvent(ClockEventType.OUT)
            onClockOutCalculateHoursWorked(clock, lastClock)

            //contact firebase and updated
            ClockEventRepository.addClockOutForUser(clock)
            //update internal storage
            updateSharedPreferencesOfLastClock(clock)
            //update ui of last clock details
            updateClockEventInfo(clock)
        }
        else
        {
            Toast.makeText(context, "You are already Clocked Out!", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * trigger event to show all clock events on the app!
     */
    private fun onViewClockEvents()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        listener?.onShowAllClockEvents()
    }

    /**
     * update the latest saved shared preference file of the last triggered clock event!
     * @param clockEvent :
     */
    private fun updateSharedPreferencesOfLastClock(clockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val sharedPrefs = this.activity!!.getSharedPreferences(mySharedPrefsEvents, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putInt(getString(R.string.pref_clockevent_event_key), clockEvent.event.value)
        editor.putLong(getString(R.string.pref_clockevent_date_key), clockEvent.dateTime)
        editor.apply()
    }

    /**
     * open, read and return the last saved clock event registered!
     * @return : a ClockEvent
     */
    private fun readSharedPreferencesForLastClock() : ClockEvent
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val sharedPref = this.activity!!.getSharedPreferences(mySharedPrefsEvents, Context.MODE_PRIVATE)
        val clock = ClockEvent()

        val output = ClockEventType.fromInt(sharedPref.getInt(getString(R.string.pref_clockevent_event_key), ClockEventType.IN.value))
        clock.event = output ?: ClockEventType.IN
        clock.dateTime = sharedPref.getLong(getString(R.string.pref_clockevent_date_key), 0)

        return clock
    }

    /**
     * Operation to update and refresh the clock event displayed information
     * @param clockEvent : the event to be used to push data out for display purposes!
     */
    private fun updateClockEventInfo(clockEvent: ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var txtClockEvent = view!!.findViewById<TextView>(R.id.txt_CurrentClockEvent)
        var txtClockEventDate = view!!.findViewById<TextView>(R.id.txt_CurrentClockEventDate)

        txtClockEvent.text = clockEvent.event.toString()
        txtClockEventDate.text = HelperMethods.convertDateTimeToString(clockEvent.dateTimeToLocalDateTime())
    }

    /**
     *
     * @param clockOutEvent :
     * @param lastClock : the last know clock event to compared
     */
    private fun onClockOutCalculateHoursWorked(clockOutEvent: ClockEvent, lastClock : ClockEvent)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var timeDiff = TimeCalculator.difference(lastClock.dateTimeToLocalDateTime(), clockOutEvent.dateTimeToLocalDateTime())
        Toast.makeText(context, "TimeDiff : ${timeDiff.hours} hours and ${timeDiff.minutes} mins", Toast.LENGTH_LONG).show()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentShowAllEventsListener {
        fun onShowAllClockEvents()
    }

    companion object {

        private var mySharedPrefsEvents = "myClockEventsPrefs"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ClockEventFragment.
         */
        @JvmStatic
        fun newInstance() =
                ClockEventFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
