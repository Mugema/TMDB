package com.example.tmdb.domain.repository

import com.example.tmdb.domain.Movies
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {

    suspend fun addMovieGenre()

    suspend fun addMovie()

    suspend fun getMovie():Flow<Movies>

    //suspend fun getGenreMovie(id:Int):List<String>

    suspend fun addMovieMGenreCrossRef()

    suspend fun addMovieCategoryCrossRef(catId:Int,id:Int)

    suspend fun addCategory()

    suspend fun getMovieCategory(id: Int):List<Int>

    suspend fun bookMark(bookMark:Boolean,id: Int)

    suspend fun getBookMarked():List<Movies>
}