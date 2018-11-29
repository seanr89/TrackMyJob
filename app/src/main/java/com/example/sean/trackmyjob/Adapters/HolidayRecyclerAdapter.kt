package com.example.sean.trackmyjob.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sean.trackmyjob.Models.Holiday
import com.example.sean.trackmyjob.R
import com.example.sean.trackmyjob.Utilities.HelperMethods
import com.google.rpc.Help
import kotlinx.android.synthetic.main.clockevent_list_item.view.*
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

        //if the current holiday is not null
        if(item != null)
        {
            holder.mStartDateView.text = HelperMethods.convertDateTimeToString(HelperMethods.convertLongToLocalDateTime(item.startDateTime))
            holder.mHoursView.text = item.hours.toString()
            holder.mDaysView.text = item.days.toString()
            holder.mMinutesView.text = item.mins.toString()
            holder.mTypeView.text = item.type.toString()
            holder.mStatusView.text = item.status.toString()
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

    /**
     * custom class for handling each individual holiday item content
     */
    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView)
    {
        val mStartDateView: TextView = mView.txtViewStartDate
        val mDaysView : TextView = mView.txtViewDays
        val mHoursView: TextView = mView.txtViewHours
        val mMinutesView: TextView = mView.txtViewMinutes
        val mTypeView: TextView = mView.txtViewHolidayType
        val mStatusView: TextView = mView.txtViewHolidayStatus
    }
}