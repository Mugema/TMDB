package com.example.tmdb.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["genreId","movieId"])
data class GenreEntity(
    val genreId:Int,
    val movieId:Int
)