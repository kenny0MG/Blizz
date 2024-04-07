package org.blink.blizz.common

import android.app.Application
import org.blink.blizz.data.firebase.FirebaseAnotherUserRepository
import org.blink.blizz.data.firebase.FirebaseCardRepository
import org.blink.blizz.data.firebase.FirebaseUserRepository

class BlissApp:Application() {
    val usersRepo by lazy { FirebaseUserRepository() }
    val cardsRepo by lazy { FirebaseCardRepository() }
    val anotherRepo by lazy { FirebaseAnotherUserRepository() }

//    val notificationsRepo by lazy { FirebaseNotificationRepository() }

    override fun onCreate() {
        super.onCreate()

        //NotificationCreator(notificationsRepo, usersRepo, feedPostsRepo)
    }
}