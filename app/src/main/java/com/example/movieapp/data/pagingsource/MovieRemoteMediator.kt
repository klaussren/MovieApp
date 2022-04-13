package com.example.movieapp.data.pagingsource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.data.database.MovieDatabase
import com.example.movieapp.data.database.entities.MovieEntity
import com.example.movieapp.data.database.entities.MovieRemoteKeys

import com.example.movieapp.data.network.MovieService
import com.example.movieapp.domain.model.Movie

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val api: MovieService,
    private val movieDatabase: MovieDatabase): RemoteMediator<Int, MovieEntity>() {


    private val getMoviesDao = movieDatabase.getMovieDao()
    private val movieRemoteKeysDao = movieDatabase.movieRemoteKeysDao()




    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieEntity>): MediatorResult {
        return try {
        val currentPage = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                prevPage
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                nextPage
            }
        }
            Log.i("currentPage",currentPage.toString())
            val response = api.getPopularMovies(page = currentPage).moviesModels
            val endOfPaginationReached = response.isEmpty()
            Log.i("endOfPag",endOfPaginationReached.toString())
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1
            Log.i("prevPage",prevPage.toString())
            Log.i("nextPage",nextPage.toString())

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    getMoviesDao.clearMovies()
                    movieRemoteKeysDao.clearRemoteKeys()
                }
                val keys = response.map {
                    MovieRemoteKeys(
                        id = it.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                Log.i("keys",keys.toString())
                movieRemoteKeysDao.insertAll(remoteKey = keys)
                getMoviesDao.insertAll(movie = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }

    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }
}








