package com.example.tmdb.domain.useCases

import com.example.tmdb.data.local.MoviesLocalDataSource
import javax.inject.Inject

class BookMarkMovie @Inject constructor(
    private val moviesLocalDataSource: MoviesLocalDataSource
) {

    suspend fun bookmarkMovie(bookMark:Boolean,id: Int){
        moviesLocalDataSource.bookMark(bookMark,id)
    }
}