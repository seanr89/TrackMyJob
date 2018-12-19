package com.example.sean.trackmyjob.Business

import android.content.Context
import android.util.Log
import android.util.Log.d
import android.util.Log.e
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class MyLocationManager
/**
 * Constructor to allow passing of context
 * @param context : hopefully the parent activity context if available!!
 */(context: Context?) {

    private val TAG = "MyLocationManager"

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    init {
        if(context != null)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     * handle the request for the current device lat and longitude
     * @param onLocated(LatLng?) : allows handling of delayed response for message
     */
    fun getDeviceLatLng(onLocated: (LatLng?) -> Unit)
    {
        //Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

        var currentLatLng : LatLng? = null
        try
        {
            fusedLocationClient.lastLocation.addOnCompleteListener()
            {
                val location = it.result
                if(location != null)
                {
                    currentLatLng = LatLng(location.latitude, location.longitude)
                    onLocated(currentLatLng)
                }
            }.addOnFailureListener()
            {
                e(TAG, "Failed to find location!! with exception ${it.message}")
                onLocated(currentLatLng)
            }
        }
        catch(e: KotlinNullPointerException)
        {
            Log.d(TAG,"${object{}.javaClass.enclosingMethod?.name} with Exception: ${e.message}")
            onLocated(null)
        }
        catch(e: SecurityException)
        {
            d(TAG,"${object{}.javaClass.enclosingMethod?.name} with Exception: ${e.message}")
            onLocated(null)
        }
    }

}