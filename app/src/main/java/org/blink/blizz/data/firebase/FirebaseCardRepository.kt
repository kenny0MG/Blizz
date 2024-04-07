package org.blink.blizz.data.firebase

import androidx.lifecycle.LiveData
import org.blink.blizz.data.CardRepository
import org.blink.blizz.data.FirebaseLiveDataSinglEvent
import org.blink.blizz.model.User
import org.blink.blizz.utils.database
import org.robotics.blinkblink.data.common.asUsers
import org.robotics.blinkblink.data.common.map


class FirebaseCardRepository: CardRepository {
    override fun getCard(): LiveData<List<User>> =
        FirebaseLiveDataSinglEvent(database.child("user")).map{

            it.children.map { it.asUsers()!! }
        }


}
