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
     *
     * @param clockEventStats
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

        }
    }

    /**
     * @param time : the time in Hours and Minutes to update too!
     */
    fun updateWeekHoursAndMinutes(time : TimeDiff)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }

    /**
     * @param time : the time in Hours and Minutes to update too!
     */
    fun updateMonthlyHoursAndMinutes(time : TimeDiff)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
    }
}