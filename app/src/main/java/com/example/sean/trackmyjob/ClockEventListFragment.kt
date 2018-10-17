package com.example.sean.trackmyjob

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sean.trackmyjob.Adapters.MyClockEventRecyclerViewAdapter
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Repositories.ClockEventRepository

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ClockEventListFragment.OnListFragmentInteractionListener] interface.
 */
class ClockEventListFragment : Fragment() {

    private val TAG = "ClockEventListFragment"
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var adapter : MyClockEventRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_clockevent_list, container, false)

        adapter = MyClockEventRecyclerViewAdapter(arrayListOf(), listener)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =  LinearLayoutManager(context)
                adapter = adapter
            }
        }

        ClockEventRepository.requestAllClockEventsForCurrentUser {
            if(!it.isEmpty())
            {
                updateClockEventAdapter(it)
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     *
     */
    private fun updateClockEventAdapter(events : MutableList<ClockEvent?>)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        adapter.updateDataSet(events)
        adapter.notifyDataSetChanged()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: ClockEvent?)
    }

    companion object {

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ClockEventListFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
