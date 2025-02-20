package com.example.tmdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie:MoviesEntity)

    @Query("Update MoviesEntity set bookMark=:bookmarked where id=:id")
    suspend fun bookMarking(bookmarked:Boolean,id:Int)

    @Transaction
    @Query("Select * FROM MoviesEntity where bookMark=1")
    fun getBookMarked(): List<GenreForMovie>

    @Transaction
    @Query("Select * FROM MoviesEntity where id =:id")
    fun getGenreForMovie(id:Int): List<GenreForMovie>

    @Query("Select * FROM MoviesEntity")
    fun getMovies(): Flow<List<GenreForMovie>>

    @Upsert
    suspend fun addCategory(category:Categories)

    @Transaction
    @Query("Select * From MovieCategoryCrossRef where id =:id")
    fun  getMoviesUnderCategory(id: Int):List<MovieCategoryCrossRef>

}