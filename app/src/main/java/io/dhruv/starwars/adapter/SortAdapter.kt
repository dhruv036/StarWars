package io.dhruv.starwars.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.dhruv.starwars.R
import io.dhruv.starwars.databinding.FilterDialogItemBinding
import io.dhruv.starwars.modal.Filter

class SortAdapter(val list: List<Filter>, private val clickedListener: SortClickedListener) : RecyclerView.Adapter<SortAdapter.SortViewHolder>()  {

    class SortViewHolder (private val binding: FilterDialogItemBinding) : RecyclerView.ViewHolder( binding.root ){

        fun bind(item: Filter, sortFilterClickListener: SortClickedListener) {
            binding.filter = item
            binding.clickListener = sortFilterClickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): SortViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FilterDialogItemBinding.inflate(layoutInflater, parent, false)
                return SortViewHolder(binding)
            }
        }
    }

    class SortClickedListener( val clickListener: (filter: Filter) -> Unit)  {
        fun onClick(filter: Filter) = clickListener(filter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        return SortViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        holder.bind(list.get(position),clickedListener)
    }
}