package org.blink.blizz.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.google.firebase.installations.Utils
import org.blink.blizz.utils.ValueEventListenerAdapter


//Обновляется при получении новых данных
class FirebaseLiveData(private val query: Query): LiveData<DataSnapshot>(){
    private val listener = ValueEventListenerAdapter {
        value = it
    }
    override fun onActive() {
        super.onActive()
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        query.removeEventListener(listener)
    }
}



//Обновляется только при первом запуске
class FirebaseLiveDataSinglEvent(private val query: Query): LiveData<DataSnapshot>(){
    private val listener = ValueEventListenerAdapter {
        value = it
    }
    override fun onActive() {
        super.onActive()
        query.addListenerForSingleValueEvent(listener)
    }

}