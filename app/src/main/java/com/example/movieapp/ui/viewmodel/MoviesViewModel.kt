package com.example.movieapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData

import androidx.paging.cachedIn
import com.example.movieapp.data.database.entities.MovieEntity
import com.example.movieapp.data.network.MovieService
import com.example.movieapp.domain.GetMovieSearchUseCase
import com.example.movieapp.domain.GetPopularMoviesUseCase
import com.example.movieapp.domain.GetTopRatedMoviesUseCase
import com.example.movieapp.domain.GetVideosMoviesUseCase
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject  constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getVideosMovies: GetVideosMoviesUseCase,
    private val getMovieSearch: GetMovieSearchUseCase

):ViewModel(){

    val movieVideo = MutableLiveData<List<Video>>()
    
    val isLoading = MutableLiveData<Boolean>()

    private var currentTopRatedResult: Flow<PagingData<Movie>>? = null
    private var currentPopularResult: Flow<PagingData<MovieEntity>>? = null
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Movie>>? = null


    fun topRatedMovies(): Flow<PagingData<Movie>> {

        val lastResult = currentTopRatedResult
        if(lastResult != null){
            return lastResult

        }
        val newResult: Flow<PagingData<Movie>> = getTopRatedMoviesUseCase().cachedIn(viewModelScope)
        currentTopRatedResult = newResult
        return newResult

    }


    fun popularMovies(): Flow<PagingData<MovieEntity>> {
        isLoading.postValue(true)
        val lastResult = currentPopularResult
        if(lastResult != null){
            isLoading.postValue(false)
            return lastResult

        }
        val newResult: Flow<PagingData<MovieEntity>> = getPopularMoviesUseCase().cachedIn(viewModelScope)
        currentPopularResult = newResult
        isLoading.postValue(false)
        return newResult

    }


    fun searchMovie(queryString: String): Flow<PagingData<Movie>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Movie>> = getMovieSearch(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }


    fun getVideosMovie(id:Int) {

        viewModelScope.launch {
            val resultVideosMovies = getVideosMovies(id)
            if(!resultVideosMovies.isNullOrEmpty()){
                movieVideo.postValue(resultVideosMovies)
            }

        }

    }





  /*  fun OnCreateTopRated(pageMovie:Int){
        viewModelScope.launch {
            isLoading.postValue(true)


            val resultTopRated = getTopRatedMoviesUseCase()



            if(!resultTopRated.isNullOrEmpty() ){
                movieModelTopRated.postValue(resultTopRated)

                isLoading.postValue(false)
            }
        }
    }*/

/*
        val listData = Pager(PagingConfig(pageSize = 1)) {

            PopularPagingSurce(apiService)

        }.flow.cachedIn(viewModelScope)
*/










}

