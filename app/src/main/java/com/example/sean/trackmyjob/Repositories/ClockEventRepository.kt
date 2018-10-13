package com.example.sean.trackmyjob.Repositories

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object ClockEventRepository
{
    private val TAG = "ClockEventRepository"
    private val firestoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

    //Operation to request the user document
//    private val currentUserClockDocsRef : DocumentReference
//        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid}/clockevent/")
    private val currentUserClockCollectionRef : CollectionReference = firestoreInstance.collection("users/${FirebaseAuth.getInstance().uid}/clockevent/")


    /**
     *
     */
    fun requestAllClockEventsForCurrentUser(onComplete:(MutableList<ClockEvent?>) -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var events = ArrayList<ClockEvent?>()

        try
        {
            currentUserClockCollectionRef
                    .get()
                    .addOnSuccessListener {
                        if(!it.isEmpty)
                        {
                            for(doc : DocumentSnapshot in it.documents)
                            {
                                val event = doc.toObject(ClockEvent::class.java)
                                if(event != null)
                                    events.add(event)
                            }
                        }
                        onComplete(events)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "get returned with error : ${it.message}")
                        onComplete(events)
                    }
        }
        catch (e : Exception)
        {
            Log.e(TAG, "Exception occurred : ${e.message}")
        }
    }

    fun getLastClockEventForUser()
    {

    }

    /**
     *
     */
    fun addClockForUser()
    {

    }

    /**
     *
     */
    fun addClockOutForUser()
    {

    }
}