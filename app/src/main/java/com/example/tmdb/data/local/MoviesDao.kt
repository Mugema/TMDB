package com.example.tmdb.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface MoviesDao {
    @Upsert
    suspend fun addMovie(movie:MoviesEntity)

    @Transaction
    @Query("Select * FROM MoviesEntity where id =:id")
    fun getGenreForMovie(id:Int): List<GenreForMovie>

    @Query("Select * FROM MoviesEntity")
    fun getMovies():List<GenreForMovie>

    @Upsert
    suspend fun addCategory(category:Categories)

    @Transaction
    @Query("Select * From Categories where categoryId=:id")
    fun  getMoviesUnderCategory(id: Int):List<CategoryMovie>

}