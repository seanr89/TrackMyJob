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

//    /**
//     * request the office lat and lng positions stored
//     * @return [LatLng] : the current Latitude and Longitude of the office (defaults to : Randox Science Park)
//     */
//    fun readOfficeLatLng() : LatLng
//    {
//        val latitude = prefs.getString(pref_office_lat_key, "54.720255")!!.toDouble()
//        val longitude = prefs.getString(pref_office_lng_key, "-6.2299717")!!.toDouble()
//        return LatLng(latitude,longitude)
//    }
//
//    /**
//     * handle updating of shared preferences with lat and lng of selected office position
//     * @param latLng : the latitude and longitude of the office to locate!
//     */
//    fun updateOfficeLatLng(latLng: LatLng)
//    {
//        val editor = prefs.edit()
//        editor.putString(pref_office_lat_key, latLng.latitude.toString())
//        editor.putString(pref_office_lng_key, latLng.longitude.toString())
//        editor.apply()
//    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    companion object {
        private const val pref_firstclocked_key = "STOREDFIRSTCLOCK"
    }
}