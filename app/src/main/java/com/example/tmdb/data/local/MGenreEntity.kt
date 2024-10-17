package com.example.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MGenreEntity(
    @PrimaryKey(autoGenerate = false) val genreId:Int,
    val genre:String
)
