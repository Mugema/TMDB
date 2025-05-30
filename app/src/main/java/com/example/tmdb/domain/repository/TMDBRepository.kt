package com.example.tmdb.domain.repository

import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.Result
import com.example.tmdb.presentation.models.Actor
import com.example.tmdb.presentation.models.Movie
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {

    suspend fun nextPage()

    suspend fun getMovie():Flow<List<Movies>>

    suspend fun bookMark(bookMark:Boolean,id: Int)

    suspend fun searchMovie(query:String): Result<List<Movie>, DataErrors>

    suspend fun searchActor(query:String): Result<List<Actor>, DataErrors>

    fun getBookMarkedMovie(): Flow<List<Movie>>

//    suspend fun searchSeries():Flow
}