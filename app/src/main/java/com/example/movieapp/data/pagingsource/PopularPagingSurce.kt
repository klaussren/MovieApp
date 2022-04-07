package com.example.movieapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.network.MovieService
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.toDomain

class PopularPagingSurce (  private val api: MovieService): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val response =  api.getPopularMovies(currentPage).moviesModels
            val responseData = mutableListOf<Movie>()
            val data = response.map { it.toDomain() }
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}