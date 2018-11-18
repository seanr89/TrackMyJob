package com.example.sean.trackmyjob.Business

import android.content.Context
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

class MyLocationManager {

    private val TAG = "MyLocationManager"

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * Constructor to allow passing of context
     * @param context : hopefully the parent activity context if available!!
     */
    constructor(context: Context?)
    {
        if(context != null)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     *
     * @return
     */
    fun getDeviceLatLng() : LatLng?
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var currentLatLng : LatLng? = null
        try
        {
            if(fusedLocationClient != null)
            {
                fusedLocationClient.lastLocation.addOnCompleteListener()
                {
                    val location = it.result
                    if(location != null)
                    {
                        currentLatLng = LatLng(location.latitude, location.longitude)
                    }
                }
            }
        }
        catch(e: SecurityException)
        {
            Log.e("Exception: %s", e.message)
        }

        return currentLatLng
    }

}