package com.example.sean.trackmyjob.Adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.sean.trackmyjob.ClockEventListFragment.OnListFragmentInteractionListener
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.R

import kotlinx.android.synthetic.main.fragment_clockeventlist.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
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
                .inflate(R.layout.fragment_clockeventlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
//        holder.mIdView.text = item.id
//        holder.mContentView.text = item.content

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    fun updateDataSet(events : MutableList<ClockEvent?>)
    {
        mValues = events
    }
}