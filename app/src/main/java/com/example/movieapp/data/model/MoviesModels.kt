package com.example.movieapp.data.model

import com.example.movieapp.data.database.entities.MovieEntity
import com.google.gson.annotations.SerializedName

data class MoviesModels(
    @SerializedName("results") val moviesModels: List<MovieEntity>
    )
