package org.blink.blizz.data.firebase

import androidx.lifecycle.LiveData
import org.blink.blizz.data.AnotherUserRepository
import org.blink.blizz.model.User
import org.blink.blizz.utils.database
import org.blink.blizz.utils.liveData
import org.robotics.blinkblink.data.common.asUsers
import org.robotics.blinkblink.data.common.map
import org.robotics.bliss.utils.NODE_USERS

class FirebaseAnotherUserRepository: AnotherUserRepository {
    override fun getUid(uid: String): LiveData<User> =
        database.child(NODE_USERS).child(uid).liveData().map {
            it.asUsers()!!
        }




}