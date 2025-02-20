package com.example.tmdb.data.repository

import android.util.Log
import com.example.tmdb.data.local.Categories
import com.example.tmdb.data.local.MovieCategoryCrossRef
import com.example.tmdb.data.local.MovieTvDb
import com.example.tmdb.data.mapper.toMGenre
import com.example.tmdb.data.mapper.toMovieGenreCrossRef
import com.example.tmdb.data.mapper.toMovies
import com.example.tmdb.data.mapper.toMoviesEntity
import com.example.tmdb.data.remote.ApiService
import com.example.tmdb.data.remote.MovieGenreDto
import com.example.tmdb.data.remote.NowPlayingMoviesDto
import com.example.tmdb.data.remote.PopularMoviesDto
import com.example.tmdb.data.remote.TopRatedDto
import com.example.tmdb.data.remote.UpComingDto
import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.repository.TMDBRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val api:ApiService,
    private val db:MovieTvDb,
    private val ioDispatcher:CoroutineDispatcher
):TMDBRepository {
    private lateinit var popularMovies:PopularMoviesDto
    private lateinit var upComingMovies:UpComingDto
    private lateinit var topRatedMovies:TopRatedDto
    private lateinit var nowPlayingMovies:NowPlayingMoviesDto

    override suspend fun addMovieGenre() {
        withContext(Dispatchers.Default){
            val genre:MovieGenreDto=api.getMovieGenres()
            for (i in 0..<genre.genres.size)
            {
                db.getGenreDao().addMovieGenre(genre.toMGenre(i))
            }
        }

    }

    override suspend fun addMovie() {
        popularMovies=api.getPopularMovies()
        upComingMovies=api.getUpComingMovies()
        topRatedMovies=api.getTopRatedMovies()
        nowPlayingMovies=api.getNowPlayingMovies()

        withContext(Dispatchers.Default){
            for(i in 0..<popularMovies.results.size){
                popularMovies.toMoviesEntity(i).forEach{entry ->
                    db.getMovieDao().addMovie(entry.key)
                    addMovieCategoryCrossRef(4,entry.key.id)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<upComingMovies.results.size){
                upComingMovies.toMoviesEntity(i).forEach{entry ->
                    db.getMovieDao().addMovie(entry.key)
                    addMovieCategoryCrossRef(2,entry.key.id)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<topRatedMovies.results.size){
                topRatedMovies.toMoviesEntity(i).forEach{entry ->
                    db.getMovieDao().addMovie(entry.key)
                    addMovieCategoryCrossRef(3,entry.key.id)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<nowPlayingMovies.results.size){
                nowPlayingMovies.toMoviesEntity(i).forEach{entry ->
                    db.getMovieDao().addMovie(entry.key)
                    addMovieCategoryCrossRef(1,entry.key.id)
                }
            }
        }
    }

    override suspend fun addMovieMGenreCrossRef() {
        withContext(Dispatchers.Default){
            for(i in 0..<popularMovies.results.size){
                popularMovies.toMovieGenreCrossRef(i).forEach { item->
                    db.getGenreDao().addMovieMGenre(item)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<upComingMovies.results.size){
                upComingMovies.toMovieGenreCrossRef(i).forEach { item->
                    db.getGenreDao().addMovieMGenre(item)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<topRatedMovies.results.size){
                topRatedMovies.toMovieGenreCrossRef(i).forEach { item->
                    db.getGenreDao().addMovieMGenre(item)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<nowPlayingMovies.results.size){
                nowPlayingMovies.toMovieGenreCrossRef(i).forEach { item->
                    db.getGenreDao().addMovieMGenre(item)
                }
            }
        }

    }

    override suspend fun addMovieCategoryCrossRef(catId:Int,id:Int) {
        withContext(Dispatchers.IO){
            db.getGenreDao().addMovieCategory(MovieCategoryCrossRef(catId,id))
        }

    }

    override suspend fun addCategory() {
        withContext(Dispatchers.IO){
            db.getMovieDao().addCategory(Categories(1,"Now Playing"))
            db.getMovieDao().addCategory(Categories(2,"Up Coming"))
            db.getMovieDao().addCategory(Categories(3,"Top Rated"))
            db.getMovieDao().addCategory(Categories(4,"Popular"))

        }
    }

    override suspend fun getMovie(): Flow<Movies> {
        return flow {
            val list = mutableListOf<Movies>()
            db.getMovieDao().getMovies().map {item->
                item.forEach { items ->
                    val movie=items.movie.toMovies(getGenre(items.movie.id),getMovieCategory(items.movie.id))
                    list.add(movie)
                }
            }
            list.forEach{emit(it)}
        }.flowOn(ioDispatcher)

    }

    private suspend fun getGenre(id:Int):List<String>{
        val genreList= mutableListOf<String>()
        withContext(Dispatchers.IO){
            db.getMovieDao().getGenreForMovie(id).forEach { genreForMovie ->
                genreForMovie.genreList.forEach{genre->
                    genreList.add(genre.genre)
                }
            }
        }
        return genreList
    }

    override suspend fun getMovieCategory(id:Int):List<Int> {
        val category= mutableListOf<Int>()
        withContext(Dispatchers.IO){
            db.getMovieDao().getMoviesUnderCategory(id).forEach {item->
                category.add(item.categoryId)
                }
            }
        return category
    }

    override suspend fun bookMark(bookMark: Boolean,id:Int) {
        withContext(Dispatchers.IO){
            db.getMovieDao().bookMarking(bookMark,id)
        }
    }

    override suspend fun getBookMarked(): List<Movies> {
        val list = mutableListOf<Movies>()
        withContext(Dispatchers.IO){
            db.getMovieDao().getBookMarked().forEach { item ->
                val movie=item.movie.toMovies(getGenre(item.movie.id))
                list.add(movie)
            }
        }
        Log.d("bookmarked",list.toString())
        return list
    }
}
