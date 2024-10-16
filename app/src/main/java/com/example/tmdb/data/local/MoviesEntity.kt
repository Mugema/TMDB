package com.example.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesEntity(
    val backdropPath: String,
    @PrimaryKey(autoGenerate = false)val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double
)
