package com.example.sean.trackmyjob.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sean.trackmyjob.Models.Holiday
import com.example.sean.trackmyjob.R
import kotlinx.android.synthetic.main.fragment_clockeventlist.view.*
import kotlinx.android.synthetic.main.holiday_recycler_item.view.*

class HolidayRecyclerAdapter(
        private var mValues: List<Holiday?>
        ) : RecyclerView.Adapter<HolidayRecyclerAdapter.ViewHolder>()
{
    private val TAG = "HolidayRecyclerAdapter"

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.holiday_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        if(item != null)
        {

        }
    }

    /**
     * handle the updating of the recycler adapter data source!
     * @param holidays : list of holiday objects
     */
    fun updateDataSet(holidays : MutableList<Holiday?>)
    {
        mValues = holidays
    }

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView)
    {
        val mStartDateView: TextView = mView.txtViewStartDate
        val mEndDateView: TextView = mView.txtViewEndDate
        val mTypeView: TextView = mView.txtViewHolidayType
        val mAttendView: TextView = mView.txtViewHolidayAttended
    }
}