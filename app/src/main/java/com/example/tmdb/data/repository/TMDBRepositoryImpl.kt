package com.example.tmdb.data.repository

import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.Result
import com.example.tmdb.domain.map
import com.example.tmdb.domain.repository.TMDBRepository
import com.example.tmdb.domain.toActor
import com.example.tmdb.presentation.models.Actor
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.presentation.models.toMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl,
    private val ioDispatcher:CoroutineDispatcher
):TMDBRepository {

    override suspend fun nextPage() {
        TODO("Not yet implemented")
    }

    override suspend fun getMovie(): Flow<List<Movies>> {
        val response = moviesRepositoryImpl.getMovies()
        return when ( response ){
            is Result.Error -> TODO()
            is Result.Success -> { return response.data}
        }
    }

    override suspend fun bookMark(bookMark: Boolean, id: Int) {
        moviesRepositoryImpl.bookMarkMovie(bookMark = bookMark, movieId = id)
    }

    override suspend fun searchMovie(query: String): Result<List<Movie>, DataErrors> {
        return moviesRepositoryImpl.searchMovie(query).map { it.map { movie-> movie.toMovie() } }
    }

    override suspend fun searchActor(query:String): Result<List<Actor>, DataErrors> {
        return moviesRepositoryImpl.searchPerson(query).map { it.map { it.toActor() } }
    }

    override  fun getBookMarkedMovie(): Flow<List<Movie>> {
        return moviesRepositoryImpl.getBookMarked().map { it.map { it.toMovie() } }
    }

}
