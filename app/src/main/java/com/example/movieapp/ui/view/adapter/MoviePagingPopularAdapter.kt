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
import com.example.movieapp.databinding.ItemMoviePopularityBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.view.fragments.HomeMovieFragmentDirections

class MoviePagingPopularAdapter : PagingDataAdapter<Movie, MoviePagingPopularAdapter.MyViewHolder>(DIFF_UTIL) {


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

    override fun onBindViewHolder(holder: MoviePagingPopularAdapter.MyViewHolder, position: Int) {
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
    ): MoviePagingPopularAdapter.MyViewHolder {

        val viewDataBinding =
            ItemMoviePopularityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewDataBinding)
    }

    inner class MyViewHolder(val viewDataBinding: ItemMoviePopularityBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun render(movieModel: Movie) {

            viewDataBinding.tvTitle.setText(movieModel.title)
            viewDataBinding.tvPopular.setText(movieModel.popularity)
            viewDataBinding.tvOriginalLanguaje.setText(movieModel.original_language)
            viewDataBinding.tvReleaseDate.setText(movieModel.release_date)
            viewDataBinding.tvVoteAverage.setText(movieModel.vote_average)



            Glide.with(viewDataBinding.ivMovie)
                .load("https://image.tmdb.org/t/p/w500" + movieModel.poster_path)
                .into(viewDataBinding.ivMovie)
            viewDataBinding.cardViewItemPopular.startAnimation(
                AnimationUtils.loadAnimation(
                    itemView.context,
                    R.anim.anim_popular
                )
            )


        }


    }
}