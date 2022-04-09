package com.example.movieapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.data.database.dao.MovieDao
import com.example.movieapp.data.database.dao.MovieRemoteKeysDao
import com.example.movieapp.data.database.entities.MovieEntity
import com.example.movieapp.data.database.entities.MovieRemoteKeys

@Database(
    entities = [MovieEntity::class,MovieRemoteKeys::class],
    version =1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun getMovieDao():MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MovieDatabase::class.java, "Movie.db")
                .build()
    }
}