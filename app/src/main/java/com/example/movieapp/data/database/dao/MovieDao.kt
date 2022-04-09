package com.example.movieapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.database.entities.MovieEntity
import com.example.movieapp.data.model.MovieModel

@Dao
interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie: List<MovieEntity>)

   @Query("SELECT * FROM movie_table")
   fun getAllMovies():PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movie_table")
    suspend fun clearMovies()
}