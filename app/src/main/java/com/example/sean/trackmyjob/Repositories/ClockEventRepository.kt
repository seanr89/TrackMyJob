package com.example.sean.trackmyjob.Repositories

import android.util.Log
import com.example.sean.trackmyjob.Models.ClockEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

object ClockEventRepository
{
    private val TAG = "ClockEventRepository"
    private val firestoreInstance : FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

    //Operation to request the clock event collection for a single user
    private val currentUserClockCollectionRef : CollectionReference = firestoreInstance.collection("users/${FirebaseAuth.getInstance().uid}/clockevents/")

    /**
     * handle the request for all stored clock events for the current user
     * @param onComplete : function type to allow synchronous event handling
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
                        if(!it.isEmpty || it != null)
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
        catch (e : IllegalArgumentException)
        {
            Log.e(TAG, "Exception occurred : ${e.message}")
        }
    }

    /**
     * @param onComplete : lambda function to allow handling of output
     */
    fun getLastClockEvent(onComplete: (ClockEvent?) -> Unit)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        var event : ClockEvent? = null
        try {
            currentUserClockCollectionRef
                    .orderBy("dateTime", Query.Direction.DESCENDING)
                    .limit(1)
                    .get()
                    .addOnSuccessListener {
                        if(!it.isEmpty || it != null)
                        {
                            //Log.d(TAG, "item found for single clock event")
                            for(doc : DocumentSnapshot in it.documents)
                            {
                                event = doc.toObject(ClockEvent::class.java)
                            }
                            onComplete(event)
                        }
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "get returned with error : ${it.message}")
                    }

        }
        catch (e : IllegalArgumentException)
        {
            Log.e(TAG, "Exception occurred : ${e.message}")
        }
        finally {
            onComplete(null)
        }
    }

    /**
     * provides operation to insert a clockEvent operation for a client
     * @param clock : the clock event to insert
     * @param onComplete :
     */
    fun addClockEventForUser(clock : ClockEvent, onComplete: (Boolean) -> Unit)
    {
        //Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        currentUserClockCollectionRef
                .add(clock)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + it.id)
                    onComplete(true)
                }
                .addOnFailureListener {
                    Log.e(TAG, "add returned with error : ${it.message}")
                    onComplete(false)
                }
    }
}