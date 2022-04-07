package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesModels(
    @SerializedName("results") val moviesModels: List<MovieModel>
    )
