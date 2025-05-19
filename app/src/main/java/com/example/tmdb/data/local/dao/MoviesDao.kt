package com.example.tmdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.local.model.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie: MoviesEntity)

    @Query("Update MoviesEntity set bookMark=:bookmarked where id=:id")
    suspend fun bookMarking(bookmarked:Boolean,id:Int)

//    @Query("Select * , GenreEntity.genreId , CategoriesEntity.categoryId from MoviesEntity " +
//            "join GenreEntity on GenreEntity.movieId " +
//            "join CategoriesEntity on CategoriesEntity.movieId")
//    fun getMovies(): Flow<List<MoviesMultiMap>>

    @Query("Select * from MoviesEntity")
    fun getMovies():Flow<List<MoviesEntity>>

    @Query("Select * from MoviesEntity")
    suspend fun checkTable():List<MoviesEntity>
}