package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: String,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("vote_average") val vote_average: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("backdrop_path") val backdrop_path: String
)
