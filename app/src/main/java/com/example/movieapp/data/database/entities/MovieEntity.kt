package com.example.movieapp.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="id")  val id: String,
    @ColumnInfo(name="title")  val title: String,
    @ColumnInfo(name="overview")  val overview: String,
    @ColumnInfo(name="popularity")  val popularity: String,
    @ColumnInfo(name="original_language")  val original_language: String?,
    @ColumnInfo(name="original_title")  val original_title: String?,
    @ColumnInfo(name="release_date")   val release_date: String?,
    @ColumnInfo(name="vote_average")  val vote_average: String?,
    @ColumnInfo(name="poster_path")  val poster_path: String?,
    @ColumnInfo(name="backdrop_path")  val backdrop_path: String?
):Parcelable
