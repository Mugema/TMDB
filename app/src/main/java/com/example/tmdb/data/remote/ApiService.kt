package com.example.tmdb.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY="a2f76f3a9afec8527beb8f1495660ad4"
interface ApiService{
    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_Key") apiKey:String= API_KEY,
        @Query("page") page:Int=1
    ):List<NowPlayingMoviesDto>

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_Key") apiKey:String= API_KEY,
        @Query("page") page:Int=1
    ):List<TopRatedDto>

    @GET("/3/movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_Key") apiKey:String= API_KEY,
        @Query("page") page:Int=1
    ):List<UpComingDto>

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_Key") apiKey:String= API_KEY,
        @Query("page") page:Int=1
    ):List<PopularMoviesDto>

    @GET("3/genre/movie/list")
    suspend fun getMovieGenres(@Query("api_Key") apiKey:String= API_KEY):MovieGenreDto

//    @GET("/3/genre/tv/list")
//    suspend fun getTvShowGenres(@Query("api_Key") apiKey:String= API_KEY):
}
