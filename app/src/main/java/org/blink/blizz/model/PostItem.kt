package org.blink.blizz.model

data class PostItem(
    val photoNumber:Int=0,

    val id:String = "",

    val type: Int = 1,

    val title: String = "",

    val dataUrl: String = ""
): java.io.Serializable