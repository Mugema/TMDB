package com.example.tmdb.domain.repository

import com.example.tmdb.domain.Movies
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {

    suspend fun addMovieGenre()

    suspend fun addMovie()

    //suspend fun getMovie():Flow<Movies>

    suspend fun addMovieMGenreCrossRef()
}