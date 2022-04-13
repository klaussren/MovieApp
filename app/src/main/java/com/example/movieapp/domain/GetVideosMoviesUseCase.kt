package com.example.movieapp.domain

import android.util.Log
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.Video
import javax.inject.Inject

class GetVideosMoviesUseCase@Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(id:String): List<Video> {
        val videosMovie = repository.getAllVideosMovie(id)
        Log.i("retorno de videos",videosMovie.toString())
        return videosMovie
    }

}