package org.blink.blizz.list.adapter

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.sample.list.adapter.PostListAdapter
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.blink.blizz.model.PostType

@ExperimentalCoroutinesApi
open class VideoRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var autoPlayEnable: Boolean = true
        set(value) {
            field = value
            playerVideo.playWhenReady = value
        }

    val playerVideo = SimpleExoPlayer.Builder(context.applicationContext)
        .build()

    var currentVideoHolder: VideoPlayerEventListener? = null
    private var videoItemHeight = 0
    private var screenHeight = 0

    init {
        with(playerVideo) {
            playWhenReady = autoPlayEnable
            repeatMode = Player.REPEAT_MODE_ALL
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> currentVideoHolder?.onLoading(true)
                        Player.STATE_READY -> currentVideoHolder?.onLoading(false)
                    }

                    super.onPlaybackStateChanged(playbackState)
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if (isPlaying) currentVideoHolder?.onPlay()
                }
            })
        }

        videoViewHolderChanges()
            .onEach { playVideo(it) }
            .launchIn((context as LifecycleOwner).lifecycleScope)

        addOnChildAttachStateChangeListener(object : OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {}

            override fun onChildViewDetachedFromWindow(view: View) {
                val holder = findContainingViewHolder(view)
                if (holder is VideoPlayerEventListener) {
                    holder.onPlayCanceled()
                }
            }
        })
        val screenSize = screenSize()
        videoItemHeight = screenSize.x
        screenHeight = screenSize.y
    }

    fun changePlayingState(play: Boolean) {
        if (play) playerVideo.play() else playerVideo.pause()
    }

    private fun playVideo(targetViewHolder: VideoPlayerEventListener?) {
        if (currentVideoHolder != null && currentVideoHolder == targetViewHolder) return
        else if (targetViewHolder == null) {
            currentVideoHolder?.onPlayCanceled()
            currentVideoHolder = null
        } else {
            currentVideoHolder?.onPlayCanceled()
            currentVideoHolder = targetViewHolder
            currentVideoHolder?.onPrePlay(playerVideo)
        }
    }

    private fun videoViewHolderChanges(): Flow<VideoPlayerEventListener?> {
        return callbackFlow {
            val listener = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    this@callbackFlow.trySend(dy).isSuccess
                }
            }
            addOnScrollListener(listener)
            awaitClose {
                removeOnScrollListener(listener)
            }
        }
            .buffer(Channel.CONFLATED)
            .mapLatest { getTargetVideoHolder() }
            .flowOn(IO)
    }

    private fun getTargetVideoHolder(): VideoPlayerEventListener? {
        try {
            val position = findCurrentVideoPosition()

            if (position == NO_POSITION) return null
            val viewHolder = findViewHolderForAdapterPosition(position)
            return if (viewHolder is VideoPlayerEventListener) viewHolder else null
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            return null
        }
    }

    private fun findCurrentVideoPosition(): Int {
        var result = NO_POSITION
        val linearLayoutManager = layoutManager as LinearLayoutManager

        val discoverAdapter = adapter as PostListAdapter
        val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
        val lastPosition = linearLayoutManager.findLastVisibleItemPosition()
        var percentMax = 0
        for (position in firstPosition..lastPosition) {
            val percent = getVisibleVideoHeight(position, linearLayoutManager)
            if (percentMax < percent) {
                percentMax = percent
                result = position
            }
        }

        return if (!discoverAdapter.isItemVideo(result)) NO_POSITION
        else result
    }

    private fun getVisibleVideoHeight(
        position: Int,
        linearLayoutManager: LinearLayoutManager
    ): Int {
        val child = linearLayoutManager.findViewByPosition(position) ?: return NO_POSITION
        val location = IntArray(2)
        child.getLocationInWindow(location)
        return if (location[1] < 0) location[1] + videoItemHeight else screenHeight - location[1]
    }

    private fun PostListAdapter.isItemVideo(position: Int): Boolean {
        return position != NO_POSITION && getItemViewType(position) == PostType.VIDEO.typeId
    }
}

interface VideoPlayerEventListener {
    fun onLoading(isLoading: Boolean)
    fun onPrePlay(player: SimpleExoPlayer)
    fun onPlayCanceled()
    fun onPlay()
}

fun View.screenSize(): Point {
    val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val point = Point()
    display.getSize(point)
    return point
}