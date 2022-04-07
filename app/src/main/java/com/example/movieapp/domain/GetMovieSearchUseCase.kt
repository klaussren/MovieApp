package com.example.movieapp.domain

import android.util.Log
import androidx.paging.PagingData
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetMovieSearchUseCase@Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(query:String): Flow<PagingData<Movie>> {
        val movieSearch = repository.getAllMovieSearch(query)
        Log.i("retorno de retrofit",movieSearch.toString())
        return movieSearch
    }

}