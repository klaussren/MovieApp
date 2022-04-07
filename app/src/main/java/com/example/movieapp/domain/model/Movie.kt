package com.example.movieapp.domain.model

import android.os.Parcelable
import com.example.movieapp.data.database.entities.MovieEntity
import com.example.movieapp.data.model.MovieModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val popularity: String,
    val original_language: String?,
    val original_title: String?,
    val release_date: String?,
    val vote_average: String?,
    val poster_path: String?,
    val backdrop_path: String?
): Parcelable

fun MovieModel.toDomain() = Movie(
    id,
    title,
    overview,
    popularity,
    original_language,
    original_title,
    release_date,
    vote_average,
    poster_path,
    backdrop_path
)

fun MovieEntity.toDomain() = Movie(
    id,
    title,
    overview,
    popularity,
    original_language,
    original_title,
    release_date,
    vote_average,
    poster_path,
    backdrop_path
)