package org.blink.blizz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import org.blink.blizz.R
import org.blink.blizz.databinding.HobieRecyclerItemBinding
import org.blink.blizz.model.Hobie

class HobieAdapter(private val onClick: (Hobie) -> Unit): ListAdapter<Hobie, HobieAdapter.ChipViewHolder>(
    DiffCallBack()
) {
    inner class ChipViewHolder(val chip: HobieRecyclerItemBinding) :
        RecyclerView.ViewHolder(chip.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun onBind(item: Hobie) {
            chip.textHobbie.text= item.name
            chip.root.setOnClickListener {
                if (!item.click){
                    item.click = true
                    onClick(item)



                }else if(item.click){
                    item.click = false
                    onClick(item)
                    chip.layoutHobbie.setBackgroundResource(R.drawable.hobie_recycler);

                }
                notifyDataSetChanged()
            }
            if(!item.click){
                chip.layoutHobbie.setBackgroundResource(R.drawable.hobie_recycler)
            }
            else{
                chip.layoutHobbie.setBackgroundResource(R.drawable.hobbie_recycler_on)
            }


        }



    }
    private class DiffCallBack : DiffUtil.ItemCallback<Hobie>() {
        override fun areItemsTheSame(oldItem: Hobie, newItem: Hobie): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Hobie, newItem: Hobie): Boolean =
            oldItem == newItem
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        return ChipViewHolder(
            HobieRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
