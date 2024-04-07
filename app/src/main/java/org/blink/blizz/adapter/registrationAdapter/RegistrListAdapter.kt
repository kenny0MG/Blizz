package mpt.kurshach.blissmeet2.adapter.registrationAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.blink.blizz.adapter.registrationAdapter.ImageRegistrViewHolder
import org.blink.blizz.databinding.ImageRegistrRecyclerItemBinding
import org.blink.blizz.databinding.VideoRegistrRecyclerItemBinding
import org.blink.blizz.model.Post
import org.blink.blizz.model.PostType

class RegistrListAdapter(private val listener: Listener, private val onClick: (Post) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataSet: MutableList<Post> = mutableListOf()

    fun updateDataSet(dataSet: List<Post>) {
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
                return ImageRegistrViewHolder(
                    ImageRegistrRecyclerItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClick
                )
            }
            PostType.VIDEO.typeId -> {
                return RegistrViewHolder(
                    VideoRegistrRecyclerItemBinding.inflate(
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
            (holder as ImageRegistrViewHolder).bind(dataSet[position],listener)
        } else if (dataSet[position].type == PostType.VIDEO.typeId) {
            (holder as RegistrViewHolder).bind(dataSet[position],listener)
        }
    }

    override fun getItemCount(): Int = dataSet.size

    class DiffCallback(
        private val oldDataSet: List<Post>,
        private val newDataSet: List<Post>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldDataSet.size
        override fun getNewListSize(): Int = newDataSet.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldDataSet[oldItemPosition].type == newDataSet[newItemPosition].type

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldDataSet[oldItemPosition] == newDataSet[newItemPosition]
    }


    interface Listener {
     fun delete_image(image:Post)


    }
}