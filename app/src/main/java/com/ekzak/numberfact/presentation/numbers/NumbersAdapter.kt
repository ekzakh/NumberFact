package com.ekzak.numberfact.presentation.numbers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ekzak.numberfact.databinding.NumberItemBinding

class NumbersAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<NumbersViewHolder>(),
    Mapper<Unit, List<NumberUi>> {

    private val list = mutableListOf<NumberUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val binding = NumberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumbersViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun map(source: List<NumberUi>) {
        val diffUtilCallback = DiffUtilCallback(list, source)
        val result = DiffUtil.calculateDiff(diffUtilCallback)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}

class NumbersViewHolder(
    private val binding: NumberItemBinding,
    private val clickListener: ClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: NumberUi) {
        with(binding) {
            model.map(title, subTitle)
        }

        itemView.setOnClickListener {
            clickListener.click(model)
        }
    }
}

interface ClickListener {
    fun click(item: NumberUi)
}

class DiffUtilCallback(
    private val oldList: List<NumberUi>,
    private val newList: List<NumberUi>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].map(newList[newItemPosition])


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}
