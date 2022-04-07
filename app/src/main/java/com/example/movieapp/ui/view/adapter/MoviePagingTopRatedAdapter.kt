package com.example.movieapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieTopRatedBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.view.fragments.HomeMovieFragmentDirections

class MoviePagingTopRatedAdapter : PagingDataAdapter<Movie, MoviePagingTopRatedAdapter.MyViewHolder>(DIFF_UTIL) {


    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MoviePagingTopRatedAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.render(item)

            holder.itemView.setOnClickListener {mView ->
                val direction = HomeMovieFragmentDirections.actionHomeMovieFragment2ToDetailMovieFragment3(item)
                mView.findNavController().navigate(direction)

            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePagingTopRatedAdapter.MyViewHolder {

        val viewDataBinding =
            ItemMovieTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewDataBinding)
    }

    inner class MyViewHolder(val viewDataBinding: ItemMovieTopRatedBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun render(movieModel: Movie) {

            viewDataBinding.tvTitle.setText(movieModel.title)
            viewDataBinding.tvVoteAverage.setText(movieModel.vote_average)



            Glide.with(viewDataBinding.ivMovie)
                .load("https://image.tmdb.org/t/p/w500" + movieModel.backdrop_path)
                .into(viewDataBinding.ivMovie)
        /*    viewDataBinding.cardViewItemTopRated.startAnimation(
                AnimationUtils.loadAnimation(
                    itemView.context,
                    R.anim.anim_popular
                )
            )*/


        }


    }
}