package com.example.sean.trackmyjob.Repositories

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.example.sean.trackmyjob.Models.ClockEventStats
import com.example.sean.trackmyjob.Models.Enums.UserStatus
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
    fun requestCurrentUserClockStatsSummary(onComplete:(ClockEventStats?) -> Unit) {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        currentUserClockStatsSummaryDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                onComplete(documentSnapshot.toObject(ClockEventStats::class.java))
            } else {
                Log.d(TAG, "No Summary Exists")
                onComplete(null)
            }
        }
    }

    /**
     * save the provided clockEventStats object to firestore for the current user
     * @param clockEventStats
     */
    fun createStatsSummary(clockEventStats: ClockEventStats)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        currentUserClockStatsSummaryDocRef.set(clockEventStats).addOnSuccessListener {
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