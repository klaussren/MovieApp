package com.example.movieapp.data.network

import android.util.Log
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.data.model.MoviesModels
import com.example.movieapp.data.model.VideosModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieService @Inject constructor(private val api:MovieApiClient){

    suspend fun getPopularMovies(page:Int): MoviesModels{
        return withContext(Dispatchers.IO) {
            val response = api.getAllPopularMovies(page)
            Log.i("retorno de service",response.toString())
            response.body() ?: MoviesModels(emptyList())
        }
    }


    suspend fun getTopRatedMovies(page:Int): MoviesModels{
        return withContext(Dispatchers.IO) {

            val response = api.getAllTopRatedMovies(page)
            Log.i("retorno de service",response.toString())
            response.body() ?: MoviesModels(emptyList())
        }
    }


    suspend fun getVideosMovie(id:String):VideosModels{
        return withContext(Dispatchers.IO) {

            val response= api.getAllVideosMovie(id)
            Log.i("retorno de videos",response.toString())
            response.body()?: VideosModels(emptyList())
        }
    }
    suspend fun getMovieSearch(query:String,page:Int):MoviesModels{
        return withContext(Dispatchers.IO) {
            val response = api.getAllMovieSearch(query,page)
            response.body() ?: MoviesModels(emptyList())
        }
    }


}