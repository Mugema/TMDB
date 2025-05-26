package com.example.tmdb.domain.useCases

import com.example.tmdb.data.local.MoviesLocalDataSource
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.presentation.models.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSelectedMovie @Inject constructor(
    private val movieLocalDataSource: MoviesLocalDataSource
) {
    suspend fun getMovieWithId(id:Int): Flow<Movie>{
        return movieLocalDataSource.getMovieWithId(id).map { it.toMovie() }
    }
}