package com.example.tmdb.domain.repository

import com.example.tmdb.domain.Movies
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {

    suspend fun nextPage()

    suspend fun getMovie():Flow<List<Movies>>

    suspend fun bookMark(bookMark:Boolean,id: Int)


//    suspend fun searchMovie(query:String):Flow<Movies>

//    suspend fun searchSeries():Flow
}