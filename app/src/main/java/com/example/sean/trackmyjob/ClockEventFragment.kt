package com.example.sean.trackmyjob

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Repositories.ClockEventRepository

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ClockEventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ClockEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ClockEventFragment : Fragment(), View.OnClickListener {

    private val TAG = "ClockEventFragment"
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_clock_event, container, false)

        view.findViewById<Button>(R.id.btn_ClockIn).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_ClockOut).setOnClickListener(this)

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_ClockIn -> onClockIn()
            R.id.btn_ClockOut -> onClockOut()
            R.id.btn_ClockList -> onViewClockEvents()
        }// ...
    }

    /**
     * add a clock in event for the current user
     */
    private fun onClockIn()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        val clock = ClockEvent(ClockEventType.IN)
        ClockEventRepository.addClockInForUser(clock)
    }

    /**
     * add a clock out event for the current user
     */
    private fun onClockOut()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        val clock = ClockEvent(ClockEventType.OUT)
        ClockEventRepository.addClockOutForUser(clock)
    }

    private fun onViewClockEvents()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ClockEventFragment.
         */
        @JvmStatic
        fun newInstance() =
                ClockEventFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
