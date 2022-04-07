package com.example.movieapp.domain

import androidx.paging.PagingData
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase@Inject constructor(private val repository: MovieRepository) {
     operator fun invoke(): Flow<PagingData<Movie>> {
        val topRatedMovies = repository.getAllTopRatedMoviesFromApi()

        return topRatedMovies
        /*  return if(quotes.isNotEmpty()){
              repository.clearQuotes()
              repository.insertQuotes(quotes.map { it.toDatabase() })
              quotes
          }else{
              repository.getAllQuotesFromDatabase()
          }*/
    }

}