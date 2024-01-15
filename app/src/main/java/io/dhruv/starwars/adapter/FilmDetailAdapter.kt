package io.dhruv.starwars.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.dhruv.starwars.R
import io.dhruv.starwars.modal.Film

class FilmDetailAdapter() : RecyclerView.Adapter<FilmDetailAdapter.FilmDetailViewHolder>() {
    var list = mutableListOf<Film>()

    class FilmDetailViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView.rootView){
        val filmName = itemView.findViewById<TextView>(R.id.filmTitle)
        val directorName = itemView.findViewById<TextView>(R.id.directorName)
        val producerName = itemView.findViewById<TextView>(R.id.producerName)
        val releaseDate = itemView.findViewById<TextView>(R.id.releaseDate)
    }

    fun addItem(film: Film){
        list.add(film)
        notifyItemInserted(list.size-1)
    }

    override fun onBindViewHolder(holder: FilmDetailViewHolder, position: Int) {
        val item = list.get(position)
        item?.let {
            it.title?.let {
                holder.filmName.text = it

            }
            it.producer?.let {
                holder.producerName.text = it

            }
            item.producer.split(",").let {
                val result = if (it.size >= 2){
                    it.joinToString("\n")
                }else {
                    item.producer
                }
                holder.producerName.text = result
            }
            it.release_date?.let {
                holder.releaseDate.text = it

            }
            it.director?.let {
                holder.directorName.text = it

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_child_item,parent,false)
        return FilmDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}