package com.example.sean.trackmyjob.Repositories

import android.util.Log
import com.example.sean.trackmyjob.Models.TimeDiff
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Time

/**
 * repository calls for event statistics
 */
object ClockEventStatsRepository {

    private val TAG = "ClockEventStatsRepository"
    private val firestoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

    private val currentUserClockStatsSummaryDocRef : DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid}/ClockStats/StatsSummary")

    /**
     * request the current statistics for the current user including :
     * hours per week, month and lifetime!
     * current week and month!
     */
    fun requestCurrentUserClockStatsSummary(onComplete:() -> Unit) {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        currentUserClockStatsSummaryDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                //Initialise the new user object and save to the firestore

            } else {
                Log.d(TAG, "No Summary Exists")
            }
        }
    }

    /**
     * @param time : the time in hours and minutes to update too!
     */
    fun updateWeekHoursAndMinutes(time : TimeDiff)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }

    /**
     * @param time : the time in hours and minutes to update too!
     */
    fun updateMonthlyHoursAndMinutes(time : TimeDiff)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }
}