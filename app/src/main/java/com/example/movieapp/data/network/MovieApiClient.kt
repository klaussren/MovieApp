package com.example.movieapp.data.network


import com.example.movieapp.data.model.MoviesModels
import com.example.movieapp.data.model.VideosModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {

   @GET("3/movie/popular?api_key=d22413a6de096b7fe139b54a0c90b97d")
    suspend fun getAllPopularMovies(@Query("page")page:Int): Response<MoviesModels>

    @GET("3/movie/top_rated?api_key=d22413a6de096b7fe139b54a0c90b97d")
    suspend fun getAllTopRatedMovies(@Query("page")page:Int): Response<MoviesModels>


    @GET("3/movie/{id}/videos?api_key=d22413a6de096b7fe139b54a0c90b97d")
    suspend fun getAllVideosMovie(@Path("id")id:String) : Response<VideosModels>

    @GET("3/search/movie?api_key=d22413a6de096b7fe139b54a0c90b97d")
    suspend fun getAllMovieSearch(@Query("query")movie:String,@Query("page")page:Int): Response<MoviesModels>

}