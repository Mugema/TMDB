package com.example.tmdb.data.mapper

import com.example.tmdb.data.local.MoviesEntity
import com.example.tmdb.data.remote.NowPlayingMoviesDto
import com.example.tmdb.data.remote.PopularMoviesDto
import com.example.tmdb.data.remote.TopRatedDto
import com.example.tmdb.data.remote.UpComingDto

fun NowPlayingMoviesDto.toNowPlayingMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path,
            posterPath = results[index].poster_path,
            title = results[index].title,
            originalTitle = results[index].original_title,
            originalLanguage = results[index].original_language,
            video = results[index].video,
            overview = results[index].overview,
            releaseDate = results[index].release_date,
            voteAverage = results[index].vote_average,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}

fun UpComingDto.toNowPlayingMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path,
            posterPath = results[index].poster_path,
            title = results[index].title,
            originalTitle = results[index].original_title,
            originalLanguage = results[index].original_language,
            video = results[index].video,
            overview = results[index].overview,
            releaseDate = results[index].release_date,
            voteAverage = results[index].vote_average,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}

fun TopRatedDto.toNowPlayingMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path,
            posterPath = results[index].poster_path,
            title = results[index].title,
            originalTitle = results[index].original_title,
            originalLanguage = results[index].original_language,
            video = results[index].video,
            overview = results[index].overview,
            releaseDate = results[index].release_date,
            voteAverage = results[index].vote_average,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}

fun PopularMoviesDto.toNowPlayingMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path,
            posterPath = results[index].poster_path,
            title = results[index].title,
            originalTitle = results[index].original_title,
            originalLanguage = results[index].original_language,
            video = results[index].video,
            overview = results[index].overview,
            releaseDate = results[index].release_date,
            voteAverage = results[index].vote_average,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}