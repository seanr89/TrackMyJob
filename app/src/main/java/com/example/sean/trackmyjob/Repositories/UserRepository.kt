package com.example.sean.trackmyjob.Repositories

import android.util.Log
import com.example.sean.trackmyjob.Models.Enums.UserStatus
import com.example.sean.trackmyjob.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

/**
 * Data Storage access object to query, insert and update user data
 * users are stored using there uid as the name of the document but is also stored as part of there object
 */
class UserRepository
{
    private val TAG = "UserRepository"
    private val firestoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

    //Operation to request the user document
    private val currentUserDocRef : DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null")}")

    /**
     * query the current user and if not stored create the user
     * @param func<onComplete(UserStatus)> : returned
     */
    fun initCurrentUserIfFirstTime(onComplete:(UserStatus) -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if(!documentSnapshot.exists())
            {
                //Initialise the new user object and save to the firestore
                val newUser = User(FirebaseAuth.getInstance().uid,FirebaseAuth.getInstance().currentUser?.displayName ?: ""
                        , FirebaseAuth.getInstance().currentUser?.email ?: ""
                        , LocalDateTime.now())
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete(UserStatus.NewUser)
                }
            }
            else
            {
                Log.d(TAG, "User Already Exists")
                onComplete(UserStatus.ExistingUser)
            }
        }
    }
}