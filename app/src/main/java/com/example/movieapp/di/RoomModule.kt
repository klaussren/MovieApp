package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    const val MOVIE_DATA_BASE ="movie_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context)= Room.databaseBuilder(context, MovieDatabase::class.java,
        MOVIE_DATA_BASE).build()

    @Singleton
    @Provides
    fun provideMovieDao(db:MovieDatabase)=db.getMovieDao()
}