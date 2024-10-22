package com.example.tmdb.domain.repository

import com.example.tmdb.domain.Movies

interface TMDBRepository {

    suspend fun addMovieGenre()

    suspend fun addMovie()

    suspend fun getMovie():List<Movies>

    //suspend fun getGenreMovie(id:Int):List<String>

    suspend fun addMovieMGenreCrossRef()
}