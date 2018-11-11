package com.example.sean.trackmyjob.Repositories

import android.util.Log
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
}