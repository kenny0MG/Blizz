package org.blink.blizz.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnFailureListener
import org.blink.blizz.registrationActivity.RegistrationViewModel

class ModelFactory(private val app: BlissApp,
                   private val commonViewModel: CommonViewModel,
                   private val onFailureListener: OnFailureListener
): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val usersRepo = app.usersRepo
        val cardsRepo = app.cardsRepo
        val anotherRepo = app.anotherRepo


       if(modelClass.isAssignableFrom(RegistrationViewModel::class.java)){
            return RegistrationViewModel(onFailureListener,usersRepo) as T
        }


        return super.create(modelClass)
    }
}