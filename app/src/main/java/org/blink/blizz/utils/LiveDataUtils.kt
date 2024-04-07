package org.blink.blizz.utils

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import org.blink.blizz.data.FirebaseLiveData
import org.blink.blizz.data.FirebaseLiveDataSinglEvent

//Обновление при получении данных
fun DatabaseReference.liveData(): LiveData<DataSnapshot> = FirebaseLiveData(this)

//Обновление только при загрузке
fun DatabaseReference.liveSingleData():LiveData<DataSnapshot> = FirebaseLiveDataSinglEvent(this)
