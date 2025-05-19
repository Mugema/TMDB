package com.example.tmdb.data.mapper

import com.example.tmdb.data.local.model.MoviesEntity
import com.example.tmdb.data.remote.models.MoviesDto

fun MoviesDto.toMoviesEntity(index:Int):Map<MoviesEntity,List<Int>>{
    val movieGenreMap = mutableMapOf<MoviesEntity,List<Int>>()
    movieGenreMap.put(
        MoviesEntity(
            id = results[index].id,
            backdropPath = results[index].backdrop_path ?: "N/A",
            posterPath = results[index].poster_path ?: "N/A",
            title = results[index].title ?: "N/A",
            originalTitle = results[index].original_title ?: "N/A",
            originalLanguage = results[index].original_language ?: "N/A",
            video = results[index].video == true,
            overview = results[index].overview ?: "Not Available",
            releaseDate = results[index].release_date ?: "UNKNOWN",
            voteAverage = results[index].vote_average ?: 5.0,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}