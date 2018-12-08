package com.example.sean.trackmyjob.Business

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Enums.ClockEventType
import com.example.sean.trackmyjob.Utilities.HelperMethods
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.analytics.FirebaseAnalytics

class PreferencesHelper(context: Context?) {

    private var prefs : SharedPreferences
    private val TAG = "PreferencesHelper"
    private var mFirebaseAnalytics: FirebaseAnalytics

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context as Context)
    }

    /**
     * Handle the request to read the last stored clock event on the app!
     * @return ClockEvent
     */
    fun readLastStoredClock() : ClockEvent
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        var clock = ClockEvent()

        val output = ClockEventType.fromInt(prefs.getInt(pref_clockevent_event_key, ClockEventType.IN.value))
        clock.event = output ?: ClockEventType.IN
        clock.dateTime = prefs.getLong(pref_clockevent_date_key, 1)

        return clock
    }

    /**
     * Handle the updating of the last stored clock event!
     * @param clockEvent :
     */
    fun updateLastStoredClock(clockEvent: ClockEvent)
    {
        //Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        val editor = prefs.edit()
        editor.putInt(pref_clockevent_event_key, clockEvent.event.value)
        editor.putLong(pref_clockevent_date_key, clockEvent.dateTime)
        editor.apply()

        val params = Bundle()
        params.putString("clock_type", clockEvent.event.toString())
        mFirebaseAnalytics.logEvent("updateLastStoredClock", params)

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * only needs to be request on first clock in
     */
    fun checkIsFirstClockForUser() : Boolean
    {
        return prefs.getBoolean(pref_firstclocked_key, true)
    }

    /**
     * only needs to be request on first clock in
     */
    fun updatePrefsOfFirstClock()
    {
        val editor = prefs.edit()
        editor.putBoolean(pref_firstclocked_key, false)
        editor.apply()
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * request the office lat and lng positions stored
     * @return [LatLng] : the current Latitude and Longitude of the office (defaults to : Randox Science Park)
     */
    fun readOfficeLatLng() : LatLng
    {
        val latitude = prefs.getString(pref_office_lat_key, "54.720255")!!.toDouble()
        val longitude = prefs.getString(pref_office_lng_key, "-6.2299717")!!.toDouble()
        return LatLng(latitude,longitude)
    }

    /**
     * handle updating of shared preferences with lat and lng of selected office position
     * @param latLng : the latitude and longitude of the office to locate!
     */
    fun updateOfficeLatLng(latLng: LatLng)
    {
        val editor = prefs.edit()
        editor.putString(pref_office_lat_key, latLng.latitude.toString())
        editor.putString(pref_office_lng_key, latLng.longitude.toString())
        editor.apply()
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    companion object {

        private const val pref_clockevent_event_key = "STOREDEVENTKEY"
        private const val pref_clockevent_date_key = "STOREDEVENTDATE"
        private const val pref_firstclocked_key = "STOREDFIRSTCLOCK"

        //Office Location
        private const val pref_office_lat_key = "STOREDLAT"
        private const val pref_office_lng_key = "STOREDLNG"
    }
}