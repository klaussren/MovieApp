package com.example.movieapp.domain.model

import com.example.movieapp.data.model.VideoModel

data class Video (
    val key: String,
    val name: String,
        )

fun VideoModel.toDomain() = Video(
    key,name)