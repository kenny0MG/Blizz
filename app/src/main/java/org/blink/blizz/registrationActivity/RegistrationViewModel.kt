package org.blink.blizz.registrationActivity

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import org.blink.blizz.data.UsersRepository
import org.blink.blizz.model.User
import java.io.File

class RegistrationViewModel(private val onFailureListener: OnFailureListener, private val usersRepository: UsersRepository):ViewModel() {

    suspend fun uploadImage(files: List<File>, paths: String):String{
        return usersRepository.uploadMedia(files,paths).toString()
    }

    fun saveUser(user: User, uid:String): Task<Void> {
        return Tasks.whenAll(usersRepository.createUser(uid, user)).addOnFailureListener(onFailureListener)
    }


}