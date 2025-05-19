package com.example.tmdb.data.local

import com.example.tmdb.data.local.dao.CategoryDao
import com.example.tmdb.data.local.dao.GenreDao
import com.example.tmdb.data.local.dao.MoviesDao
import com.example.tmdb.data.local.model.CategoriesEntity
import com.example.tmdb.data.local.model.GenreEntity
import com.example.tmdb.data.local.model.MoviesEntity
import com.example.tmdb.data.mapper.toMoviesEntity
import com.example.tmdb.data.remote.models.MoviesDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val ioDispatcher:CoroutineDispatcher,
    private val movieDao: MoviesDao,
    private val categoryDao: CategoryDao,
    private val genreDao: GenreDao
) {

    val movies: Flow<List<MoviesEntity>> =  movieDao.getMovies()

    suspend fun getGenreForMovie(movieId:Int):List<Int>{
        return genreDao.selectGenreBasedOnId(movieId).map { it.genreId }

    }

    suspend fun getCategoryForMovie(movieId:Int):List<Int>{
        return categoryDao.selectCategoriesForId(movieId).map{ it.categoryId }
    }

    suspend fun addMovies(movieList: MoviesDto,categoryId:Int) {
        withContext(Dispatchers.Default){
            for(i in 0..<movieList.results.size){
                movieList.toMoviesEntity(i).forEach{movie ->
                    movieDao.addMovie(movie.key)
                    addToGenreEntity(movie.key.id,movie.value)
                    addToCategoryEntity(movie.key.id,categoryId)
                }
            }
        }
    }

    suspend fun addToGenreEntity(movieId:Int,genre: List<Int>){
        genre.forEach { id ->
            genreDao.addMovieGenre(
                GenreEntity(id,movieId)
            )
        }
    }

    suspend fun addToCategoryEntity(movieId:Int,categoryId:Int){
        categoryDao.addCategory(
            CategoriesEntity(categoryId,movieId)
        )

    }

    suspend fun bookMark(bookMark: Boolean,id:Int) {
        withContext(ioDispatcher){
            movieDao.bookMarking(bookMark,id)
        }
    }

    suspend fun emptyTable(): Boolean{
        return if( movieDao.checkTable().size < 0 ){
            false
        } else true

    }

}