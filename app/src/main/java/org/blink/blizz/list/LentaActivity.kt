package org.blink.blizz.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator

import org.blink.blizz.list.adapter.CardsStackAdapter
import org.blink.blizz.list.adapter.VideoRecyclerView
import com.yuyakaido.android.cardstackview.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.blink.blizz.databinding.ActivityLentaBinding
import org.blink.blizz.model.PostItem
import org.blink.blizz.model.PostType

class LentaActivity : AppCompatActivity(), CardStackListener {
    private lateinit var binding: ActivityLentaBinding

    private var adapter: CardsStackAdapter = CardsStackAdapter {}
    private val manager by lazy { CardStackLayoutManager(this, this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
        initialize()

        loadPost { list ->
            val data: List<List<PostItem>> = mutableListOf<List<PostItem>>().apply {
                list.forEach { _ ->
                    this.add(list)
                }
            }

            adapter.updateDataSet(data)
        }
    }

    private fun loadPost(
        onSuccess: (MutableList<PostItem>) -> Unit
    ) {

        val postItemList = mutableListOf<PostItem>()

        postItemList.add(
            PostItem(
                type = PostType.IMAGE.typeId,
                title = "image",
                dataUrl = "https://download.samplelib.com/png/sample-boat-400x300.png"
            )
        )

        postItemList.add(
            PostItem(
                type = PostType.VIDEO.typeId,
                title = "video",
                dataUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
            )
        )


        postItemList.add(
            PostItem(
                type = PostType.IMAGE.typeId,
                title = "image",
                dataUrl = "https://download.samplelib.com/png/sample-hut-400x300.png"
            )
        )

        onSuccess(postItemList)

    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCardAppeared(view: View, position: Int) {
//        view.findViewById<VideoRecyclerView>(R.id.).changePlayingState(true)
//        view.findViewById<VideoRecyclerView>(R.id.content_list).autoPlayEnable =
//            true
        Log.d("CardStackView", "onCardAppeared: ($position)")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCardDisappeared(view: View, position: Int) {
//        view.findViewById<VideoRecyclerView>(R.id.content_list).autoPlayEnable = false
//        view.findViewById<VideoRecyclerView>(R.id.content_list).changePlayingState(false)
        Log.d("CardStackView", "onCardDisappeared: ($position)")
    }

    private fun setupButton() {
        binding.skipButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Top)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.cardStackView.swipe()
        }


        binding.rewindButton.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            binding.cardStackView.rewind()
        }


        binding.likeButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Top)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.cardStackView.swipe()
        }
    }

    private fun initialize() {
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(false)
        manager.setCanScrollVertical(false)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }
}