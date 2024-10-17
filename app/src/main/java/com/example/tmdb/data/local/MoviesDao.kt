package com.example.tmdb.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Upsert
    suspend fun addMovie(movie:MoviesEntity)

    @Transaction
    @Query("Select * FROM MoviesEntity where id =:id")
    fun getGenreForMovie(id:Int): Flow<List<GenreForMovie>>

    @Query("Select * FROM MoviesEntity")
    suspend fun getMovies():List<MoviesEntity>

}