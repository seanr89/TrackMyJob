package com.example.sean.trackmyjob


import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sean.trackmyjob.Models.ClockEventStats
import com.example.sean.trackmyjob.Models.TimeDiff
import com.example.sean.trackmyjob.Repositories.ClockEventStatsRepository
import kotlinx.android.synthetic.main.fragment_clock_event_stats.*


/**
 * A simple [Fragment] subclass.
 * Use the [ClockEventStatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ClockEventStatsFragment : Fragment() {

    private val TAG = "ClockEventStatsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_clock_event_stats, container, false)

        requestAndDisplayStatsInformation(view)

        return view
    }

    /**
     * handle request for stats information and the displaying of the relevant data
     * @param view :
     */
    private fun requestAndDisplayStatsInformation(view : View?)
    {
        d(TAG, object{}.javaClass.enclosingMethod?.name)
        ClockEventStatsRepository.requestCurrentUserClockStatsSummary {
            if(it != null)
            {
                updateUIWithStats(it)
            }
        }
    }

    /**
     * @param stats :
     */
    private fun updateUIWithStats(stats : ClockEventStats)
    {
        d(TAG, object{}.javaClass.enclosingMethod?.name)

        updateTopRecords(stats)
        updateTimeDiffRecords(stats.dailyTime, R.id.txtViewDailyHours, R.id.txtViewDailyMinutes)
        updateTimeDiffRecords(stats.weeklyTime, R.id.txtViewWeeklyHours, R.id.txtViewWeeklyMinutes)
        updateTimeDiffRecords(stats.monthlyTime, R.id.txtViewMonthlyHours, R.id.txtViewMonthlyMinutes)
    }

    /**
     *
     * @param stats :
     */
    private fun updateTopRecords(stats: ClockEventStats)
    {
        d(TAG, object{}.javaClass.enclosingMethod?.name)

        val txtViewWeek = view!!.findViewById<TextView>(R.id.txtViewWeek)
        val txtViewMonth = view!!.findViewById<TextView>(R.id.txtViewMonth)
        val txtViewYear = view!!.findViewById<TextView>(R.id.txtViewYear)

        txtViewWeek.text = stats.week.toString()
        txtViewMonth.text = stats.month
        txtViewYear.text = stats.year.toString()
    }

    /**
     *
     * @param timeDiff :
     * @param hoursID :
     * @param minutesID :
     */
    private fun updateTimeDiffRecords(timeDiff: TimeDiff, hoursID : Int, minutesID : Int)
    {
        d(TAG, object{}.javaClass.enclosingMethod?.name)

        val txtViewHours = view!!.findViewById<TextView>(hoursID)
        val txtViewMinutes = view!!.findViewById<TextView>(minutesID)

        txtViewHours.text = timeDiff.hours.toString()
        txtViewMinutes.text = timeDiff.minutes.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ClockEventStatsFragment.
         */
        @JvmStatic
        fun newInstance() =
                ClockEventStatsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
