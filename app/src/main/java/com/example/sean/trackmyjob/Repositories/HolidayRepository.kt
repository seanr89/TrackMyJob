package com.example.sean.trackmyjob.Repositories

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.Holiday
import com.example.sean.trackmyjob.Models.HolidayStats
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.StringBuilder
import java.time.LocalDateTime

/**
 * repository object to handle holiday data access!
 */
object HolidayRepository {

    private val TAG = "HolidayRepository"
    private val firestoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

    private val userHolidayCollectionRef : CollectionReference = firestoreInstance.collection("users/${FirebaseAuth.getInstance().uid}/holidays/")


    /**
     * requesting the number of holidays remaining for the user
     */
    fun getRemainingHolidayCountForCurrentUser(onComplete:() -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }

    /**
     * request the user holiday stats for the current year
     * function requests the current year
     */
    fun getHolidayStatsForCurrentUserAndYear(onComplete:() -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val year = LocalDateTime.now().year

        try {
            userHolidayCollectionRef.document(year.toString())
                    .get()
                    .addOnSuccessListener {
                        if(it.exists())
                        {
                            onComplete()
                        }
                        else
                        {
                            val stats = HolidayStats()
                            userHolidayCollectionRef.document(year.toString()).set(stats).addOnSuccessListener {
                                onComplete()
                            }
                        }
                    }
                    .addOnFailureListener{

                    }
        }catch (e : IllegalArgumentException)
        {
            Log.e(TAG, "Exception occurred : ${e.message}")
        }
    }

    /**
     *
     */
    fun getAllUserHolidaysForYear(year:Int, onComplete: (MutableList<Holiday?>) -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        try {
            var holidays = ArrayList<Holiday?>()

            userHolidayCollectionRef.document(year.toString()).collection("holidays")
                    .get()
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener{

                    }

        }catch (e : IllegalArgumentException)
        {
            Log.e(TAG, "Exception occurred : ${e.message}")
        }
    }
}