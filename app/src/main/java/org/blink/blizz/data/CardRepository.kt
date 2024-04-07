package org.blink.blizz.data

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import org.blink.blizz.model.User

interface CardRepository{
    fun getCard(): LiveData<List<User>>
}
