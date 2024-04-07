package org.blink.blizz.fragment.cardFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import org.blink.blizz.list.adapter.CardsStackAdapter
import org.blink.blizz.list.adapter.VideoRecyclerView
import com.yuyakaido.android.cardstackview.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.blink.blizz.common.viewModel.baseFragment.BaseFragment
import org.blink.blizz.R
import org.blink.blizz.databinding.FragmentCardBinding
import org.blink.blizz.model.PostItem
import org.blink.blizz.model.PostType


class CardFragment : BaseFragment(), CardStackListener {
    private var _binding: FragmentCardBinding? = null;
    private val binding get() = _binding!!;

    private var adapter: CardsStackAdapter = CardsStackAdapter {}
    private val manager by lazy { CardStackLayoutManager(context, this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentCardBinding.inflate(inflater,container,false);
        val view = binding.root;

        loadPost { list ->
            val data: List<List<PostItem>> = mutableListOf<List<PostItem>>().apply {
                list.forEach { _ ->
                    this.add(list)
                }
            }

            adapter.updateDataSet(data)
        }
        setupButton()
        initialize()
        return view;
    }
    private fun loadPost(
        onSuccess: (MutableList<PostItem>) -> Unit
    ) {

        val postItemList = mutableListOf<PostItem>()

        postItemList.add(
            PostItem(
                type = PostType.IMAGE.typeId,
                title = "image",
                dataUrl = "https://gas-kvas.com/grafic/uploads/posts/2023-09/1695792586_gas-kvas-com-p-kartinki-kotiki-26.jpg"
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
                dataUrl = "https://gas-kvas.com/grafic/uploads/posts/2023-09/1695792586_gas-kvas-com-p-kartinki-kotiki-26.jpg"
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
        view.findViewById<VideoRecyclerView>(R.id.content_list).changePlayingState(true)
       // view.findViewById<MultiProgressBar>(R.id.mpb_main).start()
        view.findViewById<VideoRecyclerView>(R.id.content_list).autoPlayEnable =
            true
        Log.d("CardStackView", "onCardAppeared: ($position)")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCardDisappeared(view: View, position: Int) {
        view.findViewById<VideoRecyclerView>(R.id.content_list).autoPlayEnable = false
        view.findViewById<VideoRecyclerView>(R.id.content_list).changePlayingState(false)
        //view.findViewById<MultiProgressBar>(R.id.mpb_main).clear()
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
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
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