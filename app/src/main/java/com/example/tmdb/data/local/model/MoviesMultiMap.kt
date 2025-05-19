package com.example.tmdb.data.local.model

import androidx.room.Embedded

data class MoviesMultiMap(
    @Embedded val movie: MoviesEntity,
    val genreId:String,
    val categoryId:String
)
