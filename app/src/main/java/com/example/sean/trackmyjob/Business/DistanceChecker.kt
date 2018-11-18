package com.example.sean.trackmyjob.Business

import android.location.Location
import com.google.android.gms.maps.model.LatLng


object DistanceChecker {

    private val TAG = "DistanceChecker"
    private const val DISTANCE_LIMIT = 500

    /**
     * query and return if the current location is near the default office location
     * @param onComplete :
     */
    fun isNearLocation(currentLatLng : LatLng?) : Boolean
    {
        if(currentLatLng != null)
        {
            val officeLatLng = getOfficeLocation()
            val selectedLocation = Location("")
            selectedLocation.latitude = officeLatLng.latitude
            selectedLocation.longitude = officeLatLng.longitude

            val currentLocation = Location("")
            currentLocation.latitude = currentLatLng.latitude
            currentLocation.longitude = currentLatLng.longitude

            val distance = currentLocation.distanceTo(selectedLocation)
            return distance < DISTANCE_LIMIT
        }
        return null!!
    }

    /**
     * get the office location - currently hardcoded to the Randox Science Park
     */
    private fun getOfficeLocation() : LatLng
    {
        return LatLng(54.720255,-6.2299717)
    }
}