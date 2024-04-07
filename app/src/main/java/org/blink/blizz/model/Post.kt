package org.blink.blizz.model

data class Post(
    val photoNumber:Int=0,
    val id:String = "",
    val type: Int = 1,
    val dataUrl: String = ""
): java.io.Serializable