package com.example.movieapp.ui.view.fragments

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.FragmentDetailMovieBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.view.adapter.VideoMovieAdapter
import com.example.movieapp.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private lateinit var detailMovieBinding: FragmentDetailMovieBinding

    private val args: DetailMovieFragmentArgs by navArgs()
    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailMovieBinding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return detailMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Descripcion Pelicula"

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :OnBackPressedCallback(false){
            override fun handleOnBackPressed() {

            }
        })

        populateUI()

    }

    fun populateUI(){
        movie = args.movie

        Glide.with(detailMovieBinding.ivMovieDetail)
            .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .into(detailMovieBinding.ivMovieDetail)
        detailMovieBinding.tvTitleDetail.text = movie.title
        detailMovieBinding.tvReleaseDateDetail.text = movie.release_date
        detailMovieBinding.tvRate.text= movie.vote_average

        var textOverview:String

        if(movie.overview.length>=210){
            textOverview = movie.overview.substring(0,210)+"..."
            detailMovieBinding.tvDescriptionDetail.text = textOverview

            detailMovieBinding.tvVieMore.isVisible = true
            detailMovieBinding.tvVieMore.setOnClickListener {
                val mParams = detailMovieBinding.tvDescriptionDetail.layoutParams as ConstraintLayout.LayoutParams
                mParams.apply {
                    width = 560
                    height = 770
                }
                detailMovieBinding.tvDescriptionDetail.layoutParams = mParams
                detailMovieBinding.tvDescriptionDetail.filters += InputFilter.LengthFilter(260)

                detailMovieBinding.tvDescriptionDetail.text = movie.overview.substring(0,211)
                detailMovieBinding.tvDescriptionDetail2.isVisible = true
                detailMovieBinding.tvDescriptionDetail2.text = movie.overview.substring(211)
                detailMovieBinding.tvVieMore.isVisible = false
            }

        }
        else
        {
            textOverview = movie.overview
            detailMovieBinding.tvDescriptionDetail.text = textOverview
            detailMovieBinding.tvVieMore.isVisible = false
            detailMovieBinding.tvDescriptionDetail2.isVisible = false
        }


        moviesViewModel.getVideosMovie(movie.id)
        moviesViewModel.movieVideo.observe(viewLifecycleOwner, Observer {

            detailMovieBinding.rvVideosMovie.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            detailMovieBinding.rvVideosMovie.adapter = VideoMovieAdapter(it)
            detailMovieBinding.rvVideosMovie.adapter!!.notifyDataSetChanged()


        })

    }



}