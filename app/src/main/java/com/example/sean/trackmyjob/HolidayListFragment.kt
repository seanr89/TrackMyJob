package com.example.sean.trackmyjob

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sean.trackmyjob.Adapters.HolidayRecyclerAdapter

/**
 * A simple [Fragment] subclass.
 * to handle interaction events.
 * Use the [HolidayListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HolidayListFragment : Fragment() {

    private val TAG = "HolidayListFragment"
    private lateinit var adapter : HolidayRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_holiday_list, container, false)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HolidayListFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HolidayListFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
