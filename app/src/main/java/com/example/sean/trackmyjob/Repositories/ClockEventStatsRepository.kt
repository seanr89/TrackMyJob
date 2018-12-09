package com.example.sean.trackmyjob.Repositories

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEventStats
import com.example.sean.trackmyjob.Models.TimeDiff
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

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
     * Hours per Week, Month and lifetime!
     * current Week and Month!
     * @param onComplete([ClockEventStats?])
     */
    fun requestCurrentUserClockStatsSummary(onComplete:(ClockEventStats?) -> Unit) {
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)

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
        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)
        currentUserClockStatsSummaryDocRef.set(clockEventStats).addOnSuccessListener {
        }
    }

    /**
     * handle the updating of the current stats for daily, weekly and monthly information
     * @param clockEventStats : stats update to be used to update the entire object
     */
    fun updateClockEventStatsSummary(clockEventStats: ClockEventStats)
    {
        currentUserClockStatsSummaryDocRef.update(
            "Week", clockEventStats.Week,
                "Month", clockEventStats.Month,
                "Year", clockEventStats.Year,
                "DailyTime.Hours", clockEventStats.DailyTime.Hours,
                "DailyTime.Minutes", clockEventStats.DailyTime.Minutes,
                "WeeklyTime.Hours", clockEventStats.WeeklyTime.Hours,
                "WeeklyTime.Minutes", clockEventStats.WeeklyTime.Minutes,
                "MonthlyTime.Hours", clockEventStats.MonthlyTime.Hours,
                "MonthlyTime.Minutes", clockEventStats.MonthlyTime.Minutes
                ).addOnSuccessListener {
                Log.d(TAG, "stats updated")
        }.addOnFailureListener {
            Log.d(TAG, "Failed to update clock event stats!")
        }
    }

    /**
     *
     */
    fun archiveWeeklyStats(){

        Log.d(TAG, object{}.javaClass.enclosingMethod?.name)
    }
}