package org.blink.blizz.utils

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import org.blink.blizz.R
import org.blink.blizz.model.GalleryItem
import java.io.File
import java.math.RoundingMode
import java.security.Security
import java.util.Properties
import javax.activation.DataHandler
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.util.ByteArrayDataSource
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class Utils {

}



class ValueEventListenerAdapter(val handler: (DataSnapshot) -> Unit) : ValueEventListener {
    private val TAG = "ValueEventListenerAdapt"

    override fun onDataChange(data: DataSnapshot) {
        handler(data)
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e(TAG, "onCancelled: ", error.toException())
    }
}


fun ImageView.fromUrl(url: String?) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(false)
        .encodeQuality(100)
        .format(DecodeFormat.PREFER_ARGB_8888)
        .placeholder(R.drawable.empty_box)
        .into(this)
}


fun fetchImages(activity: Activity): java.util.ArrayList<GalleryItem> {
    val imageList: java.util.ArrayList<GalleryItem> = java.util.ArrayList()

    val columns = arrayOf(
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATE_ADDED

    )
    val imagecursor: Cursor = activity.managedQuery(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
        null, ""
    )
    for (i in 0 until imagecursor.count) {
        imagecursor.moveToPosition(i)
        val dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA)
        val dateColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)


        imageList.add(
            GalleryItem(
                imagecursor.getString(dataColumnIndex),
                imagecursor.getLong(dateColumnIndex)
            )
        )
    }
    return imageList
}

fun fetchVideos(activity: Activity): java.util.ArrayList<GalleryItem> {
    val videoList: java.util.ArrayList<GalleryItem> = java.util.ArrayList()

    val columns = arrayOf(
        MediaStore.Video.Media.DATA,
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.DATE_ADDED,

        )
    val imagecursor: Cursor = activity.managedQuery(
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null,
        null, ""
    )
    for (i in 0 until imagecursor.count) {
        imagecursor.moveToPosition(i)
        val dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Video.Media.DATA)
        val dateColumnIndex = imagecursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)
        val sizeColumnIndex = imagecursor.getColumnIndex(MediaStore.Video.Media.DURATION)
        videoList.add(
            GalleryItem(
                imagecursor.getString(dataColumnIndex),
                imagecursor.getLong(dateColumnIndex),
                true,
                imagecursor.getLong(sizeColumnIndex)
            )
        )
    }
    return videoList
}



suspend fun uploadImage(
    file: File, path: String,
    callback: (String?, Exception?) -> Unit,
): String = suspendCoroutine { coroutine ->


    val fileUri = Uri.fromFile(file)
    val storageRef = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("${path}/images/${fileUri.lastPathSegment}")

   imageRef.putFile(fileUri)
        .addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                callback(uri.toString(), null) // Передаем ссылку на изображение через callback// Возвращаем путь загруженного изображения
        }
        .addOnFailureListener {
            coroutine.resumeWithException(it) // Возвращаем ошибку загрузки
        }


    }

}


fun getPersonality(number: Double, size: Int): Double {
    val personality = number / size

    //Округление
    return personality.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
}


