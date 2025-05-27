package com.example.tmdb.data.remote

import com.example.tmdb.data.remote.models.ActorDto
import com.example.tmdb.data.remote.models.MoviesDto
import com.example.tmdb.domain.Category
import com.example.tmdb.domain.DataErrors
import com.example.tmdb.domain.Result

interface ApiService{

    companion object {
         const val API_KEY="a2f76f3a9afec8527beb8f1495660ad4"
         const val BASE_URL = "https://api.themoviedb.org"

        const val NOW_PLAYING = "/3/movie/now_playing"
        const val UPCOMING = "/3/movie/upcoming"
        const val TOP_RATED =  "/3/movie/top_rated"
        const val POPULAR  = "/3/movie/popular"
        const val SEARCH_MOVIE = "/3/search/movie"
        const val SEARCH_TV = "/3/search/tv"
        const val SEARCH_PERSON = "/3/search/person"
    }

    suspend fun getMovies(category: Category,page:Int=1): Result<MoviesDto, DataErrors.RemoteError>

    suspend fun searchForMovie(query: String) : Result<MoviesDto, DataErrors.RemoteError>

    suspend fun searchForPerson(query: String) : Result<ActorDto, DataErrors.RemoteError>

//    suspend fun searchForTv(query:String)


//    @GET("/3/genre/movie/list")
//    suspend fun getMovieGenres(): MovieGenreDto


//    @GET("/3/genre/tv/list")
//    suspend fun getTvShowGenres(@Query("api_Key") apiKey:String= API_KEY):
}
