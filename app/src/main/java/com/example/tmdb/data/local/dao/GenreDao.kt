package com.example.tmdb.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tmdb.data.local.model.GenreEntity

@Dao
interface GenreDao {
    @Upsert
    suspend fun addMovieGenre(genreEntity: GenreEntity)

    @Query("Select * from GenreEntity where movieId=:id")
    suspend fun selectGenreBasedOnId(id:Int): List<GenreEntity>

}