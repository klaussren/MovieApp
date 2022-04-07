package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class VideosModels (

    @SerializedName("results") val videosModels: List<VideoModel>
)
