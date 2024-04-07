package org.robotics.blinkblink.data.common

import com.google.firebase.database.DataSnapshot
import org.blink.blizz.model.User


fun DataSnapshot.asUsers(): User?=
    getValue(User::class.java)!!.copy(uid = key.toString())