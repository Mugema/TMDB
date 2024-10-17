package com.example.tmdb.data.local

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface GenreDao {
    @Upsert
    suspend fun addMovieGenre(movieGenre:MGenreEntity)

    @Upsert
    suspend fun addMovieMGenre(mGenre:MovieMGenreCrossRef)

    @Upsert
    suspend fun addMovieCategory(movieCategory:MovieMGenreCrossRef)
}