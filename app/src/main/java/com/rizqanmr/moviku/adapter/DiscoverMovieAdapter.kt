package com.rizqanmr.moviku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.moviku.R
import com.rizqanmr.moviku.databinding.ItemMovieBinding
import com.rizqanmr.moviku.network.model.ItemMovieModel
import com.rizqanmr.moviku.utils.setFitImageUrl

class DiscoverMovieAdapter : PagingDataAdapter<ItemMovieModel, DiscoverMovieAdapter.MovieViewHolder>(
    DIFF_CALLBACK
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemMovieModel>() {
            override fun areItemsTheSame(oldItem: ItemMovieModel, newItem: ItemMovieModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemMovieModel, newItem: ItemMovieModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    private lateinit var movieListener: MovieListener

    fun setMovieListener(movieListener: MovieListener) {
        this.movieListener = movieListener
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindData(item, movieListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(movie: ItemMovieModel?, listener: MovieListener) {
            (binding as? ItemMovieBinding)?.let { itemMovie ->
                itemMovie.item = movie
                with(itemMovie) {
                    ivMovie.setFitImageUrl(movie?.getUrlPoster(), R.drawable.ic_error)
                    cvMovie.setOnClickListener { listener.onItemClick(itemMovie, movie) }
                }
            }
        }
    }

    interface MovieListener {
        fun onItemClick(itemMovieBinding: ItemMovieBinding, movie: ItemMovieModel?)
    }
}