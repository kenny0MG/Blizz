package org.blink.blizz.adapter.registrationAdapter

import androidx.recyclerview.widget.RecyclerView
import mpt.kurshach.blissmeet2.adapter.registrationAdapter.RegistrListAdapter

import org.blink.blizz.databinding.ImageRegistrRecyclerItemBinding
import org.blink.blizz.model.Post
import org.blink.blizz.utils.fromUrl


class ImageRegistrViewHolder(private val binding: ImageRegistrRecyclerItemBinding, val onClick: (Post) -> Unit ) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(postItem: Post, listener: RegistrListAdapter.Listener,) {
        binding.deleteImage.setOnClickListener { listener.delete_image(postItem) }

        binding.imageRegistr.fromUrl(postItem.dataUrl)

    }
}