package com.example.tmdb.data.local

import android.util.Log
import com.example.tmdb.data.local.dao.CategoryDao
import com.example.tmdb.data.local.dao.GenreDao
import com.example.tmdb.data.local.dao.MoviesDao
import com.example.tmdb.data.local.dao.PersonalityDao
import com.example.tmdb.data.local.model.CategoriesEntity
import com.example.tmdb.data.local.model.GenreEntity
import com.example.tmdb.data.local.model.MoviesEntity
import com.example.tmdb.data.local.model.PersonalityEntity
import com.example.tmdb.data.mapper.toMovies
import com.example.tmdb.data.mapper.toMoviesEntity
import com.example.tmdb.data.remote.models.KnownForDto
import com.example.tmdb.data.remote.models.MoviesDto
import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val ioDispatcher:CoroutineDispatcher,
    private val movieDao: MoviesDao,
    private val categoryDao: CategoryDao,
    private val genreDao: GenreDao,
    private val personalityDao: PersonalityDao
) {

    val movies: Flow<List<MoviesEntity>> =  movieDao.getMovies()
    val tag = "MovieLocalDataSource"

    suspend fun getGenreForMovie(movieId:Int):List<Int>{
        return genreDao.selectGenreBasedOnId(movieId).map { it.genreId }

    }

    fun getBookMarkedMovies(): Flow<List<Movies>>{
        return  movieDao.getBookMarkedMovies().map { it.map { it.toMovies(
            genre = getGenreForMovie(it.id),
            category = getCategoryForMovie(it.id)
        ) } }
    }
    suspend fun getMovieWithId(id:Int): Flow<Movies>{
        return movieDao.getMovieWithId(id).map {
            it.toMovies(
            genre = getGenreForMovie(id),
            category = getCategoryForMovie(id)
        ) }
    }

    suspend fun moviesForPersonality(id:Int): List<Movies>{
        var movies: List<Movies>
        withContext(ioDispatcher) {
            movies = personalityDao.getMoviesForPersonality(id).map {
                it.movies.toMovies(
                genre = getGenreForMovie(it.movies.id),
                category = getCategoryForMovie(it.movies.id) )
            }
        }
        Log.d(tag,"Size:${movies.size} Movies:$movies ")
        return movies
    }

    suspend fun searchPersonality(query:String) : Result<List<PersonalityEntity>, DataErrors.LocalError> {
        val personalities = personalityDao.getPersonalityBasedOnName(query)

        return if (personalities.isEmpty()) Result.Error(DataErrors.LocalError.NOTFOUND)
        else Result.Success(personalities.distinctBy { it.actorId })
    }

    suspend fun searchMovie(query:String) : Result<List<MoviesEntity>, DataErrors.LocalError>{
        val movies = movieDao.searchBasedOnTitle(query)

        return  if (movies.isEmpty()) Result.Error(DataErrors.LocalError.NOTFOUND)
        else Result.Success(movies)
    }



    suspend fun addPersonality(personality: PersonalityEntity){
        personalityDao.addPersonality(personality)
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

    suspend fun addMovies(movieList: List<KnownForDto>, category:Int =1 ){
        withContext(Dispatchers.Default){
            movieList.forEach { movie ->
                movie.toMoviesEntity().forEach {
                    movieDao.addMovie(it.key)
                    addToGenreEntity(it.key.id,it.value)
                    addToCategoryEntity(it.key.id,category)
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

    suspend fun notEmptyTable(): Boolean{
        return movieDao.checkTable().isNotEmpty()
    }

}