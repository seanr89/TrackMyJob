package com.example.sean.trackmyjob

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sean.trackmyjob.Adapters.HolidayRecyclerAdapter
import com.example.sean.trackmyjob.Models.Holiday
import com.example.sean.trackmyjob.Repositories.HolidayRepository
import java.time.LocalDateTime

/**
 * A simple [Fragment] subclass.
 * to handle interaction events.
 * Use the [HolidayListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HolidayListFragment : Fragment() {

    private lateinit var _adapter : HolidayRecyclerAdapter

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

        _adapter = HolidayRecyclerAdapter(arrayListOf())

        HolidayRepository.getAllUserHolidaysForYear(LocalDateTime.now().year){
            if(!it.isEmpty())
            {
                refreshAdapterData(it)
            }
        }

        return view
    }

    /**
     * handle the refreshing of adapter data
     * @param data : list of nullable holidays
     */
    private fun refreshAdapterData(data : MutableList<Holiday?>)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        adapter.updateDataSet(data)
        adapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HolidayListFragment.
         */
        @JvmStatic
        fun newInstance() =
                HolidayListFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
