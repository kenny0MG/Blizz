package org.blink.blizz.data

import android.media.Image
import android.net.Uri
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import org.blink.blizz.model.Hobie
import org.blink.blizz.model.PostItem
import org.blink.blizz.model.User
import java.io.File

interface UsersRepository {

    fun getSingleEventUser():LiveData<User>

    fun createUser(uid:String,user:User):Task<Unit>

    suspend fun uploadMedia(files: List<File>, paths: String):List<String>
}