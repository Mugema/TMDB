package com.example.tmdb.data.repository

import com.example.tmdb.data.local.MGenreEntity
import com.example.tmdb.data.local.MovieMGenreCrossRef
import com.example.tmdb.data.local.MovieTvDb
import com.example.tmdb.data.local.MoviesEntity
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val api:ApiService,
    private val db:MovieTvDb,
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
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<upComingMovies.results.size){
                upComingMovies.toMoviesEntity(i).forEach{entry ->
                    db.getMovieDao().addMovie(entry.key)

                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<topRatedMovies.results.size){
                topRatedMovies.toMoviesEntity(i).forEach{entry ->
                    db.getMovieDao().addMovie(entry.key)

                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<nowPlayingMovies.results.size){
                nowPlayingMovies.toMoviesEntity(i).forEach{entry ->
                    db.getMovieDao().addMovie(entry.key)

                }
            }
        }
    }

//    override suspend fun getMovie(): Flow<Movies> {
//        for (movieEntity in db.getMovieDao().getMovies()){
//            movieEntity.toMovies()
//        }
//
//    }

    override suspend fun addMovieMGenreCrossRef() {
        withContext(Dispatchers.Default){
            for(i in 0..<popularMovies.results.size){
                popularMovies.toMovieGenreCrossRef(i).forEach { item->
                    db.getGenreDao().addMovieCategory(item)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<upComingMovies.results.size){
                upComingMovies.toMovieGenreCrossRef(i).forEach { item->
                    db.getGenreDao().addMovieCategory(item)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<topRatedMovies.results.size){
                topRatedMovies.toMovieGenreCrossRef(i).forEach { item->
                    db.getGenreDao().addMovieCategory(item)
                }
            }
        }
        withContext(Dispatchers.Default){
            for(i in 0..<nowPlayingMovies.results.size){
                nowPlayingMovies.toMovieGenreCrossRef(i).forEach { item->
                    db.getGenreDao().addMovieCategory(item)
                }
            }
        }


    }
}