package com.example.sean.trackmyjob.Business

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng


object DistanceChecker {

    private val TAG = "DistanceChecker"
    private const val DISTANCE_LIMIT = 500

    /**
     * query and return if the current location is near the default office location
     * @param onComplete :
     */
    fun isNearLocation(currentLatLng : LatLng?, officeLatLng: LatLng) : Boolean
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)
        if(currentLatLng != null)
        {
            //val officeLatLng = getOfficeLocation()
            val selectedLocation = Location("")
            selectedLocation.latitude = officeLatLng.latitude
            selectedLocation.longitude = officeLatLng.longitude

            val currentLocation = Location("")
            currentLocation.latitude = currentLatLng.latitude
            currentLocation.longitude = currentLatLng.longitude

            val distance = currentLocation.distanceTo(selectedLocation)

            return distance < DISTANCE_LIMIT
        }
        return false
    }

//    /**
//     * get the office location
//     * @return the current Latitude and Longitude of the Randox Science Park
//     */
//    private fun getOfficeLocation() : LatLng
//    {
////        val prefs = PreferencesHelper()
////        var officeLatLng = prefs.readOfficeLatLng()
////        return officeLatLng
//        return LatLng(54.720255,-6.2299717)
//    }
}