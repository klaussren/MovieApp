package com.example.movieapp.data

import android.util.Log
import androidx.paging.*
import com.example.movieapp.data.database.MovieDatabase
import com.example.movieapp.data.database.entities.MovieEntity

import com.example.movieapp.data.model.VideoModel
import com.example.movieapp.data.network.MovieService
import com.example.movieapp.data.pagingsource.MovieRemoteMediator

import com.example.movieapp.data.pagingsource.SearchPagingSource
import com.example.movieapp.data.pagingsource.TopRatedPagingSource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.Video
import com.example.movieapp.domain.model.toDomain
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieService,
    private val movieDataBase:MovieDatabase
) {


    fun getAllTopRatedMoviesFromApi():Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopRatedPagingSource(api) }
        ).flow
    }

   @OptIn(ExperimentalPagingApi::class)
     fun getAllPopularMoviesFromApi(): Flow<PagingData<MovieEntity>> {


         val pagingSourceFactory = {
             movieDataBase.getMovieDao().getAllMovies()
         }


         return Pager(
             config = PagingConfig(
                 pageSize = NETWORK_PAGE_SIZE

             ),remoteMediator = MovieRemoteMediator(
                 api = api,
                 movieDatabase = movieDataBase

             ),
             pagingSourceFactory =  pagingSourceFactory//{ PopularPagingSurce(api) }
         ).flow
     }

    /*

       fun getAllPopularMoviesFromApi(): Flow<PagingData<Movie>> {

           return Pager(
               config = PagingConfig(
                   pageSize = NETWORK_PAGE_SIZE,
                   enablePlaceholders = false
               ),

               pagingSourceFactory =    { PopularPagingSurce(api) }
           ).flow
       }  */

    suspend fun getAllVideosMovie(id:String):List<Video>{
        val response: List<VideoModel> = api.getVideosMovie(id).videosModels
        Log.i("retorno de videos",response.toString())
        return response.map {it.toDomain()}
    }



    fun getAllMovieSearch(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(api, query) }
        ).flow
    }



    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }


}