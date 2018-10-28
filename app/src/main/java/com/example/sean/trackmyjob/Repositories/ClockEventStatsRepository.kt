package com.example.sean.trackmyjob.Repositories

import com.google.firebase.firestore.FirebaseFirestore

/**
 * repository calls for event statistics
 */
object ClockEventStatsRepository {

    private val TAG = "ClockEventStatsRepository"
    private val firestoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

}