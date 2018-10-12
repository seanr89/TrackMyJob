package com.example.sean.trackmyjob.Models

import java.time.LocalDateTime
import java.io.Serializable

data class User(var uid : String?,
                val name : String,
                val email : String,
                val registrationDate : LocalDateTime = LocalDateTime.now()
                ) : Serializable
{
    //constructor() : this("","","",true, LocalDateTime.now())
    //constructor(id:String, name:String, email: String, isPrivate: Boolean) : this(id, name,email, isPrivate, LocalDateTime.now())

    /**
     * Calculate the age of the current user
     * @return integer value
     */
//    fun age() : Int
//    {
//        return UtilityMethods.calculateAge(this.dob)
//    }
}