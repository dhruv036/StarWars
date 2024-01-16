package io.dhruv.starwars.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.dhruv.starwars.R
import io.dhruv.starwars.databinding.CharacterChildItemBinding
import io.dhruv.starwars.databinding.FilmChildItemBinding
import io.dhruv.starwars.modal.Film

class FilmDetailAdapter : RecyclerView.Adapter<FilmDetailAdapter.FilmDetailViewHolder>() {
    var list = mutableListOf<Film>()

    class FilmDetailViewHolder(private val binding: FilmChildItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(film: Film){
            binding.film = refactorData(film)
        }
        companion object {
            fun from(parent: ViewGroup): FilmDetailViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =FilmChildItemBinding.inflate(layoutInflater,parent,false)
                return FilmDetailViewHolder(binding)
            }
        }
        fun refactorData(film : Film): Film? {
            return Film(
                title = film.title,
                director = film.director,
                producer =   film.producer.split(",").let {
                     if (it.size >= 2){
                        it.joinToString("\n")
                    }else {
                        film.producer
                    }
                },
                release_date = film.release_date
            )
        }
    }

    fun addItem(film: Film){
        list.add(film)
        notifyItemInserted(list.size-1)
    }

    override fun onBindViewHolder(holder: FilmDetailViewHolder, position: Int) {
        val item = list.get(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmDetailViewHolder {
        return FilmDetailViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}