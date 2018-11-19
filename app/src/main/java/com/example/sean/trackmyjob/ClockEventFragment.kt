package com.example.sean.trackmyjob

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sean.trackmyjob.Business.ClockEventManager
import com.example.sean.trackmyjob.Business.ClockEventStatsManager
import com.example.sean.trackmyjob.Business.PreferencesHelper
import com.example.sean.trackmyjob.Business.TimeCalculator
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Repositories.ClockEventRepository
import com.example.sean.trackmyjob.Utilities.HelperMethods

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ClockEventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * and [ClockEventStatsFragment.OnFragmentShowAllHolidaysListener] as well
 * Use the [ClockEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ClockEventFragment : Fragment(), View.OnClickListener {

    private val TAG = "ClockEventFragment"
    private var listener: OnFragmentShowAllEventsListener? = null
    private var listenerShowHolidays : OnFragmentShowAllHolidaysListener? = null
    private var listenerShowStats : OnFragmentShowStats? = null

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
        if(context is OnFragmentShowAllHolidaysListener)
        {
            listenerShowHolidays = context
        }else {
            throw RuntimeException(context.toString() + " must implement OnFragmentShowAllHolidaysListener")
        }
        if(context is OnFragmentShowStats)
        {
            listenerShowStats = context
        }
        else
        {
            throw RuntimeException(context.toString() + " must implement OnFragmentShowStats")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        listenerShowHolidays = null
        listenerShowStats = null
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_ClockIn -> onClockIn()
            R.id.btn_ClockOut -> onClockOut()
            R.id.btn_ClockList -> onViewClockEvents()
            R.id.btn_HolidayList -> onViewHolidays()
            R.id.btn_ClockStats -> onViewClockEventStats()
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

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////


    /**
     * add a clock in event for the current user
     */
    private fun onClockIn()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        val clockManager = ClockEventManager(context)
        val clock = ClockEvent(ClockEventType.IN)
        clockManager.saveClock(clock)

        updateClockEventInfo(clock)
    }

    /**
     * add a clock out event for the current user
     */
    private fun onClockOut()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val clockManager = ClockEventManager(context)
        val clock = ClockEvent(ClockEventType.OUT)
        clockManager.saveClock(clock)

        updateClockEventInfo(clock)
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////


    /**
     * trigger event to show all clock events on the app!
     */
    private fun onViewClockEvents()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        listener?.onShowAllClockEvents()
    }

    /**
     * trigger event to show all holidays for the user!!
     */
    private fun onViewHolidays()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        //listener?.onShowAllClockEvents()
        Toast.makeText(context, "Not Yet Available!", Toast.LENGTH_SHORT).show()
    }

    /**
     * trigger event to show the clock event stats
     */
    private fun onViewClockEventStats()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        listenerShowStats?.onShowEventStats()
        Toast.makeText(context, "Not Yet Available!", Toast.LENGTH_SHORT).show()
    }

    /**
     * Operation to update and refresh the clock event displayed information
     * @param clockEvent : the event to be used to push data out for display purposes!
     */
    private fun updateClockEventInfo(clockEvent: ClockEvent)
    {
        //Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var txtClockEvent = view!!.findViewById<TextView>(R.id.txt_CurrentClockEvent)
        var txtClockEventDate = view!!.findViewById<TextView>(R.id.txt_CurrentClockEventDate)

        txtClockEvent.text = clockEvent.event.toString()
        txtClockEventDate.text = HelperMethods.convertDateTimeToString(clockEvent.dateTimeToLocalDateTime())
    }

    /**
     * handle the calculation of hours worked from the two dates
     * @param clockOutEvent : the clock event when clocked out (OUT)
     * @param lastClock : the last know clock event to compared (IN)
     */
    private fun onClockOutCalculateHoursWorked(clockOutEvent: ClockEvent, lastClock : ClockEvent)
    {
        //Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var timeDiff = TimeCalculator.difference(lastClock.dateTimeToLocalDateTime(), clockOutEvent.dateTimeToLocalDateTime())
        Toast.makeText(context, "TimeDiff : ${timeDiff.hours} hours and ${timeDiff.minutes} mins", Toast.LENGTH_LONG).show()
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

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

    /**
     *
     */
    interface  OnFragmentShowAllHolidaysListener{
        fun onShowAllHolidays()
    }

    interface OnFragmentShowStats
    {
        fun onShowEventStats()
    }


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

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
