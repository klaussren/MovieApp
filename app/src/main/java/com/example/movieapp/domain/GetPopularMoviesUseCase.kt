package com.example.movieapp.domain

import android.util.Log
import androidx.paging.PagingData
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.data.database.entities.MovieEntity
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository:MovieRepository) {
     operator fun invoke(): Flow<PagingData<MovieEntity>> {
        val popularMovies = repository.getAllPopularMoviesFromApi()
        Log.i("retorno de retrofit",popularMovies.toString())

      return popularMovies
      /*  return if(quotes.isNotEmpty()){
            repository.clearQuotes()
            repository.insertQuotes(quotes.map { it.toDatabase() })
            quotes
        }else{
            repository.getAllQuotesFromDatabase()
        }*/
    }

}