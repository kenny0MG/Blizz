package mpt.kurshach.blissmeet2.list.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import org.blink.blizz.databinding.PostImageItemBinding
import org.blink.blizz.model.PostItem
import org.blink.blizz.utils.fromUrl

class PostImageViewHolder(
    private val binding: PostImageItemBinding,
    val onClick: (PostItem) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(postItem: PostItem) {
        binding.root.setOnClickListener { onClick(postItem) }
        binding.image.fromUrl(postItem.dataUrl)
        Log.d("LogItem",postItem.dataUrl)
    }
}