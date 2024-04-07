package org.blink.blizz.model

data class GalleryItem(
    val url: String,
    val dateCreated: Long,
    val isVideo: Boolean = false,
    val videoDuration: Long = -1
)