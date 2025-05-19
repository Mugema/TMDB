package com.example.tmdb.data.repository

import android.util.Log
import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.Result
import com.example.tmdb.domain.repository.TMDBRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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


}
