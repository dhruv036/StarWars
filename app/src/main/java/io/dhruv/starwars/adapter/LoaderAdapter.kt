package io.dhruv.starwars.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.dhruv.starwars.R

class LoaderAdapter: LoadStateAdapter<LoaderAdapter.LoaderViewHolder>()  {

    class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
        private val errorTextView = itemView.findViewById<TextView>(R.id.error_textView)

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }
    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.network_loader, parent, false)
        return LoaderViewHolder(view)
    }
}