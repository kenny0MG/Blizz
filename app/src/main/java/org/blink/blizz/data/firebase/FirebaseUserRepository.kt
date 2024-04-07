package org.blink.blizz.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.blink.blizz.data.UsersRepository
import org.blink.blizz.common.toUnit
import org.blink.blizz.model.User
import org.blink.blizz.utils.currentUid
import org.blink.blizz.utils.database
import org.blink.blizz.utils.liveSingleData
import org.blink.blizz.utils.uploadImage
import org.robotics.blinkblink.data.common.asUsers
import org.robotics.blinkblink.data.common.map
import org.robotics.bliss.utils.NODE_USERS
import java.io.File


class FirebaseUserRepository: UsersRepository {

    override fun getSingleEventUser(): LiveData<User> =
        database.child(NODE_USERS).child(currentUid()!!).liveSingleData().map {
            it.asUsers()!!
        }

    override fun createUser(uid:String,user:User): Task<Unit> =  database.child(NODE_USERS).child(uid).setValue(user).toUnit()


    override suspend fun uploadMedia(files: List<File>, paths: String): List<String> =
        coroutineScope {
            val uploadTasks = files.map { file ->
                async {
                    uploadImage(file, paths){imageUrl, exception ->
                        if (exception != null) {
                            // Обработка ошибки загрузки
                            Log.d("ErrorUri","Ошибка при загрузке изображения: ${exception.message}")
                        } else {

                            Log.d("ErrorUri","Ошибка при загрузке изображения: $imageUrl")

                        }

                    }
                }
            }
            uploadTasks.awaitAll()
        }

}


