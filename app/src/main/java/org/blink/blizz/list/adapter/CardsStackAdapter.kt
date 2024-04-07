package org.blink.blizz.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import app.sample.list.adapter.PostListAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.blink.blizz.databinding.CardStackItemBinding
import org.blink.blizz.model.PostItem

class CardsStackAdapter(private val onClick: (PostItem) -> Unit) :
    RecyclerView.Adapter<CardsStackAdapter.CardsViewHolder>() {
    private var dataSet: MutableList<List<PostItem>> = mutableListOf()

    private val viewPool = RecyclerView.RecycledViewPool()


    fun updateDataSet(dataSet: List<List<PostItem>>) {
        val diffCallback = DiffCallback(this.dataSet, dataSet)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.dataSet.clear()
        this.dataSet.addAll(dataSet)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CardsViewHolder(val binding: CardStackItemBinding) :
        ViewHolder(binding.root) {
        @OptIn(ExperimentalCoroutinesApi::class)
        fun bind(data: List<PostItem>) {
            binding.contentList.autoPlayEnable = layoutPosition == 0
            binding.contentList.setRecycledViewPool(viewPool)
            binding.contentList.layoutManager =
                LinearLayoutManager(binding.root.context, HORIZONTAL, false)

            binding.contentList.adapter = PostListAdapter(onClick).apply {
                this.updateDataSet(data)
            }

//            binding.buttonMore.setOnClickListener {
//                it.isVisible = false
//                //binding.morePanel.isVisible = true
//            }

            /*
               TODO: Доскролл руками, Глючит, надо доделать, если нужно
               binding.contentList.clearOnScrollListeners()
                binding.contentList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                        val closestItem = if (dx > 0) lastVisibleItem else firstVisibleItem

                        // Прокрутка к ближайшему элементу
                        recyclerView.smoothScrollToPosition(closestItem)
                        binding.mpbMain.start(closestItem)

                        Log.d("TAG_TEST", "onScrolled: $closestItem,  ${this@CardsViewHolder}")
                    }
                })
    */
//            binding.mpbMain.setProgressStepsCount(data.size)
//            binding.mpbMain.setListener(null)
//            binding.mpbMain.setListener(object : MultiProgressBar.ProgressStepChangeListener {
//                override fun onProgressStepChange(newStep: Int) {
//                    binding.contentList.smoothScrollToPosition(newStep)
//                }
//            })
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CardsViewHolder {
        return CardsViewHolder(
            CardStackItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    class DiffCallback(
        private val oldDataSet: List<List<PostItem>>,
        private val newDataSet: List<List<PostItem>>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldDataSet.size
        override fun getNewListSize(): Int = newDataSet.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldDataSet[oldItemPosition] == newDataSet[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldDataSet[oldItemPosition] == newDataSet[newItemPosition]
    }
}