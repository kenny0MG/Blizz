package mpt.kurshach.blissmeet2.adapter.registrationAdapter

import android.view.View
import android.widget.CompoundButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import org.blink.blizz.databinding.VideoRegistrRecyclerItemBinding
import org.blink.blizz.model.Post

import java.util.concurrent.TimeUnit

class RegistrViewHolder(private val binding: VideoRegistrRecyclerItemBinding, val onClick: (Post) -> Unit) :
    RecyclerView.ViewHolder(binding.root), VideoPlayerEventListener,  CompoundButton.OnCheckedChangeListener{
    private var item: Post? = null

    fun bind(videoItem: Post,listener: RegistrListAdapter.Listener) {
        item = videoItem

        binding.deleteVideo.setOnClickListener {
           listener.delete_image(videoItem)
        }

        Glide.with(itemView.context)
            .load(videoItem.dataUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.itemVideoPlayerThumbnail)



        binding.checkboxVolume.setOnCheckedChangeListener(this)

    }

    override fun onLoading(isLoading: Boolean) {
        binding.progressCircular.isVisible = isLoading
    }

    override fun onPrePlay(player: SimpleExoPlayer) {
        binding.itemVideoPlayer.visibility = View.GONE
        binding.itemVideoPlayerThumbnail.visibility = View.VISIBLE
        binding.timeLeft.visibility = View.GONE
        binding.checkboxVolume.visibility = View.GONE
        //play video
        with(player) {
            playVideo()
            binding.itemVideoPlayer.player = this
        }
    }

    override fun onPlayCanceled() {
        onLoading(false)
        binding.timeLeft.visibility = View.GONE
        binding.checkboxVolume.visibility = View.GONE

        binding.itemVideoPlayer.player?.stop()
        binding.itemVideoPlayer.player = null

        binding.itemVideoPlayer.visibility = View.GONE
        binding.itemVideoPlayerThumbnail.visibility = View.VISIBLE
    }

    private fun getTimeLeft() {
        binding.root.postDelayed({
            binding.timeLeft.text = calculateTimeLeft(
                binding.itemVideoPlayer.player?.contentDuration?.minus(
                    binding.itemVideoPlayer.player?.currentPosition ?: 0
                ) ?: 0
            )
            getTimeLeft()
        }, 1000)
    }

    override fun onPlay() {
        getTimeLeft()

        binding.root.postDelayed({
            if (binding.itemVideoPlayer.player != null) {
                binding.checkboxVolume.setOnCheckedChangeListener(null)
                binding.checkboxVolume.isChecked = binding.itemVideoPlayer.player!!.volume  != 1f
                binding.checkboxVolume.setOnCheckedChangeListener(this)

                binding.timeLeft.visibility = View.VISIBLE
                binding.checkboxVolume.visibility = View.VISIBLE
                binding.itemVideoPlayer.visibility = View.VISIBLE
                binding.itemVideoPlayerThumbnail.visibility = View.INVISIBLE
            }
        }, DELAY_BEFORE_HIDE_THUMBNAIL) // wait to be sure the texture view is render
    }

    private fun SimpleExoPlayer.playVideo() {
        stop(true)
        val videoUrl = item?.dataUrl ?: return
        setMediaItem(MediaItem.fromUri(videoUrl))
        prepare()
    }

    companion object {
        const val DELAY_BEFORE_HIDE_THUMBNAIL = 500L

        fun calculateTimeLeft(timeLeft: Long): String {
            return String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft)
            )
        }
    }


    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        binding.itemVideoPlayer.player?.volume = if (isChecked) 0f else 0f
    }
}