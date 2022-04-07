package com.example.movieapp.di

import com.example.movieapp.data.network.MovieApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
           // .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        val client =  OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor()).build()
        return  client

    }

    @Singleton
    @Provides
    fun provideMovieApiClient(retrofit: Retrofit):MovieApiClient{
        return retrofit.create(MovieApiClient::class.java)
    }
}