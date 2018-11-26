package com.example.sean.trackmyjob.Repositories

import com.google.firebase.firestore.FirebaseFirestore

/**
 * repository accessor for office/work public holidays!
 */
object PublicHolidayRepository {

    private val TAG = "PublicHolidayRepository"
    private val firestoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

    
}