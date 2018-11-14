package com.example.sean.trackmyjob


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Use the [ClockEventStatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ClockEventStatsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clock_event_stats, container, false)
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
