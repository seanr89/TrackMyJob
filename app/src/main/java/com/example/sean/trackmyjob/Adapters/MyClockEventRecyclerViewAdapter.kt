package com.example.sean.trackmyjob.Adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.sean.trackmyjob.ClockEventListFragment.OnListFragmentInteractionListener
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.R
import com.example.sean.trackmyjob.Utilities.HelperMethods
import com.google.rpc.Help

import kotlinx.android.synthetic.main.clockevent_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [ClockEvent] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyClockEventRecyclerViewAdapter(
        private var mValues: List<ClockEvent?>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyClockEventRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ClockEvent
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.clockevent_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        val automatic = if(item!!.automatic) "Automatic" else "Manual"
        holder.mviewEventType.text = item.event.toString()
        holder.mviewEventDate.text = HelperMethods.convertDateTimeToString(HelperMethods.convertLongToLocalDateTime(item.dateTime))
        holder.mviewEventSubType.text = item.subType.toString()
        holder.mviewEventAutomatic.text = automatic

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    fun updateDataSet(events : MutableList<ClockEvent?>)
    {
        mValues = events
    }

    override fun getItemCount(): Int = mValues.size

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mviewEventType: TextView = mView.txtViewEventType
        val mviewEventDate: TextView = mView.txtViewEventDate
        val mviewEventSubType: TextView = mView.txtViewEventSubType
        val mviewEventAutomatic: TextView = mView.txtViewEventAutomatic


//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
    }
}
