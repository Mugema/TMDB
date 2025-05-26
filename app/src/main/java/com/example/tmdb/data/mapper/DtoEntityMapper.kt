package com.example.tmdb.data.mapper

import com.example.tmdb.data.local.model.MoviesEntity
import com.example.tmdb.data.local.model.PersonalityEntity
import com.example.tmdb.data.remote.models.MoviesDto
import com.example.tmdb.data.remote.models.ResultActorDto

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
            voteAverage = results[index].vote_average ?: 0.0,
            voteCount = results[index].vote_count ?: 0,
        ),
        results[index].genre_ids
    )
    return movieGenreMap
}

fun ResultActorDto.toPersonalityEntity(movieId:Int): PersonalityEntity{
    return PersonalityEntity(
        adult = adult,
        gender = gender,
        id = id,
        knownFor = movieId,
        knownForDepartment = known_for_department,
        name = name,
        originalName = original_name,
        popularity = popularity,
        profilePath = profile_path
    )
}