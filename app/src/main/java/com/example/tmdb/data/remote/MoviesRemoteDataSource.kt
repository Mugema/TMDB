package com.example.tmdb.data.remote

import com.example.tmdb.data.remote.models.ActorDto
import com.example.tmdb.data.remote.models.MoviesDto
import com.example.tmdb.domain.Category
import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Result
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getMovies(page:Int,category: Category): Pair<Category, Result<MoviesDto, DataErrors.RemoteError>>{

        val movie = apiService.getMovies(category,page)
        return Pair(category,movie)
    }

    suspend fun searchForMovieBasedOnQuery(query:String):Result<MoviesDto, DataErrors.RemoteError>{
        return apiService.searchForMovie(query)
    }

    suspend fun searchForPersonalityBasedOnQuery(query:String): Result<ActorDto, DataErrors.RemoteError>{
        return apiService.searchForPerson(query)
    }
}