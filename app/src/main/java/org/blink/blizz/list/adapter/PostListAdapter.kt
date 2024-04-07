package app.sample.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mpt.kurshach.blissmeet2.list.adapter.PostImageViewHolder
import org.blink.blizz.databinding.PostImageItemBinding
import org.blink.blizz.databinding.PostVideoItemBinding
import org.blink.blizz.model.PostItem
import org.blink.blizz.model.PostType

class PostListAdapter(private val onClick: (PostItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataSet: MutableList<PostItem> = mutableListOf()

    fun updateDataSet(dataSet: List<PostItem>) {
        val diffCallback = DiffCallback(this.dataSet, dataSet)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.dataSet.clear()
        this.dataSet.addAll(dataSet)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        when (viewType) {
            PostType.IMAGE.typeId -> {
                return PostImageViewHolder(
                    PostImageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClick
                )
            }
            PostType.VIDEO.typeId -> {
                return PostVideoViewHolder(
                    PostVideoItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClick
                )
            }
            else -> {
                throw java.lang.IllegalArgumentException("No valid post type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int = dataSet[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (dataSet[position].type == PostType.IMAGE.typeId) {
            (holder as PostImageViewHolder).bind(dataSet[position])
        } else if (dataSet[position].type == PostType.VIDEO.typeId) {
            (holder as PostVideoViewHolder).bind(dataSet[position])
        }
    }

    override fun getItemCount(): Int = dataSet.size

    class DiffCallback(
        private val oldDataSet: List<PostItem>,
        private val newDataSet: List<PostItem>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldDataSet.size
        override fun getNewListSize(): Int = newDataSet.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldDataSet[oldItemPosition].type == newDataSet[newItemPosition].type

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldDataSet[oldItemPosition] == newDataSet[newItemPosition]
    }
}