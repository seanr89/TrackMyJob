package com.example.sean.trackmyjob.Adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sean.trackmyjob.Models.Holiday

class HolidayRecyclerAdapter(
        private var mValues: List<Holiday?>
        ) : RecyclerView.Adapter<HolidayRecyclerAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView)
    {

    }
}