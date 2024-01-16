package io.dhruv.starwars.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.TypeConverters
import io.dhruv.starwars.R
import io.dhruv.starwars.constant.ListConverter
import io.dhruv.starwars.constant.RandomColors
import io.dhruv.starwars.databinding.CharacterChildItemBinding
import io.dhruv.starwars.db.entity.CharacterEntity
import io.dhruv.starwars.modal.Filter

class CharacterAdapter (private var myContext: Context, private val clickedListener: CharacterClickedListener) : PagingDataAdapter<CharacterEntity, CharacterAdapter.CharacterPagingViewHolder>(
    CHARACTERCOMPARATOR
) {

    class CharacterPagingViewHolder(private val binding: CharacterChildItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharacterEntity, listener: CharacterClickedListener) {
            val color = RandomColors.getRandomColor()
            binding.cardLayout.setBackgroundColor(Color.parseColor(color))
            binding.character = refactorData(item)
            binding.clickListener = listener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): CharacterPagingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CharacterChildItemBinding.inflate(layoutInflater, parent, false)
                return CharacterPagingViewHolder(binding)
            }
        }

        fun refactorData(item: CharacterEntity): CharacterEntity {
            return CharacterEntity(
                name = returnName(item),
                height = returnHeight(item),
                gender = returnGender(item).toString(),
                mass = returnMass(item),
                filmList = item.filmList,
                created = item.created,
                edited = item.edited,
                id = item.id
            )
        }

        fun returnName(item: CharacterEntity): String {
            item.name.split(" ").let {
                return if (it.size == 1) {
                    it.joinToString("\n") + "\n"
                } else if (it.size == 2) {
                    it.take(2).joinToString("\n")
                } else if (it.size >= 3) {
                    it.take(2).joinToString("\n") + "..."
                } else {
                    item.name
                }
            }
        }

        fun returnHeight(item: CharacterEntity): Int? {
            return if (item.height != null) {
                item.height
            } else {
                null
            }
        }

        fun returnMass(item: CharacterEntity): Int? {
            return if (item.mass != null) {
                item.mass
            } else {
                null
            }
        }

        fun returnGender(item: CharacterEntity): String? {
            return if (item.gender!!.length <= 6) {
                item.gender
            } else {
                item.gender.substring(0..3).plus("...")
            }
        }

    }

    override fun onBindViewHolder(holder: CharacterPagingViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, clickedListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterPagingViewHolder {
        return CharacterPagingViewHolder.from(parent)
    }

    companion object {
        private val CHARACTERCOMPARATOR = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(
                oldItem: CharacterEntity,
                newItem: CharacterEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CharacterEntity,
                newItem: CharacterEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }



    class CharacterClickedListener(val clickListener: (character: CharacterEntity) -> Unit) {
        fun onClick(character: CharacterEntity) = clickListener(character)


    }
}