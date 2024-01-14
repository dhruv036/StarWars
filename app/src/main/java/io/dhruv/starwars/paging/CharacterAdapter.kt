package io.dhruv.starwars.paging

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.dhruv.starwars.R
import io.dhruv.starwars.constant.RandomColors
import io.dhruv.starwars.db.entity.CharacterEntity

class CharacterAdapter(var myContext: Context) : PagingDataAdapter<CharacterEntity, CharacterAdapter.CharacterPagingViewHolder>(CHARACTERCOMPARATOR) {

    var onCLick: ((CharacterEntity) -> Unit)? = null

    class CharacterPagingViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview.rootView){

        val charName = itemview.findViewById<TextView>(R.id.characterName)
        val charHeight = itemview.findViewById<TextView>(R.id.charHeight)
        val charGender = itemview.findViewById<TextView>(R.id.charGender)
        val charWeight = itemview.findViewById<TextView>(R.id.charWeight)

        val constraintLayout = itemview.findViewById<ConstraintLayout>(R.id.cardLayout)

    }

    override fun onBindViewHolder(holder: CharacterPagingViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.constraintLayout.setBackgroundColor(myContext.getColor(RandomColors.getRandomColor()))
            item.name.split(" ").let {
                val result = if (it.size == 1){
                    it.joinToString("\n")+"\n"
                }else if (it.size == 2) {
                    it.take(2).joinToString("\n")
                }else if (it.size >= 3) {
                    it.take(2).joinToString("\n") + "..."
                } else {
                    item.name
                }
                holder.charName.text = result
            }
            holder.charHeight.text = if(item.height != null){
                item.height.toString()
            }else{
               "n/a"
            }
            holder.charGender.text = if(item.gender!!.length <= 6){
                item.gender
            }else{
                item.gender.substring(0..3).plus("...")
            }
            holder.charWeight.text = if(item.mass != null){
                item.mass.toString()
            }else{
                "n/a"
            }
        }
        holder.itemView.rootView.setOnClickListener{
            onCLick?.let {
                getItem(position)?.let { it1 -> it(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterPagingViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.character_child_item,parent,false)
        return CharacterPagingViewHolder(viewHolder)
    }

    companion object {
        private val CHARACTERCOMPARATOR = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
    fun onItemSelected(listener: (CharacterEntity) -> Unit) {
        onCLick = listener
    }

}