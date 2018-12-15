package com.example.sean.trackmyjob

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sean.trackmyjob.Business.ClockEventManager
import com.example.sean.trackmyjob.Business.PreferencesHelper
import com.example.sean.trackmyjob.Business.TimeCalculator
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Repositories.ClockEventRepository
import com.example.sean.trackmyjob.Utilities.HelperMethods
import java.time.LocalDateTime

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
    private var listenerShowStats : OnFragmentShowStatsListener? = null

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
        view.findViewById<Button>(R.id.btn_HolidayList).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_ClockList).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_ClockStats).setOnClickListener(this)

        setLastKnownClockEventOnUI(view)
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
        if(context is OnFragmentShowStatsListener) {
            listenerShowStats = context
        }
        else {
            throw RuntimeException(context.toString() + " must implement OnFragmentShowStatsListener")
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
     * @param view : the parent view to access and update txtView content
     * @param clockEvent : the current clock event parameters
     */
    private fun setLastKnownClockEventOnUI(view : View?, clockEvent: ClockEvent? = null)
    {
        //Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        if(clockEvent != null)
        {
            updateClockEventInfo(view ?: this.view, clockEvent)
            return
        }
        ClockEventRepository.getLastClockEvent {
            updateClockEventInfo(view ?: this.view, it)
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
        val clockManager = ClockEventManager(context)
        val clock = ClockEvent(ClockEventType.IN)
        clockManager.saveClock(clock){ it, lastClock ->
            if(it)
            {
                setLastKnownClockEventOnUI(view, clock)
                clockManager.triggerUpdateOfClockEventStats(clock, lastClock)
            }
            else{
                Toast.makeText(context, "Clock In Failed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * add a clock out event for the current user
     */
    private fun onClockOut()
    {
        val clockManager = ClockEventManager(context)
        val clock = ClockEvent(ClockEventType.OUT)
        clockManager.saveClock(clock){ it, lastClock ->
            if(it)
            {
                setLastKnownClockEventOnUI(view, clock)
                clockManager.triggerUpdateOfClockEventStats(clock, lastClock)
            }
            else
            {
                Toast.makeText(context, "Clock Out Failed!", Toast.LENGTH_LONG).show()
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////


    /**
     * trigger event to show all clock events on the app!
     */
    private fun onViewClockEvents()
    {
        listener?.onShowAllClockEvents()
    }

    /**
     * trigger event to show all holidays for the user!!
     */
    private fun onViewHolidays()
    {
        listenerShowHolidays?.onShowAllHolidays()
    }

    /**
     * trigger event to show the clock event stats
     */
    private fun onViewClockEventStats()
    {
        listenerShowStats?.onShowEventStats()
    }

    /**
     * Operation to update and refresh the clock event displayed information
     * @param clockEvent : the event to be used to push data out for display purposes!
     */
    private fun updateClockEventInfo(view : View?, clockEvent: ClockEvent?)
    {
        var txtClockEvent = view!!.findViewById<TextView>(R.id.txt_CurrentClockEvent)
        var txtClockEventDate = view!!.findViewById<TextView>(R.id.txt_CurrentClockEventDate)

        txtClockEvent.text = clockEvent?.event?.toString() ?: "Unknown"
        txtClockEventDate.text = HelperMethods.convertDateTimeToString(clockEvent?.dateTimeToLocalDateTime() ?: LocalDateTime.now())
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////


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
    interface OnFragmentShowAllHolidaysListener{
        fun onShowAllHolidays()
    }

    interface OnFragmentShowStatsListener
    {
        fun onShowEventStats()
    }


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    companion object {

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
