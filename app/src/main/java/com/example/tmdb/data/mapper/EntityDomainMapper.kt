package com.example.tmdb.data.mapper

import com.example.tmdb.data.local.model.MoviesEntity
import com.example.tmdb.data.local.model.PersonalityEntity
import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.Personality

fun MoviesEntity.toMovies(genre:List<Int>,category: List<Int> = listOf()):Movies{
    return Movies(
        backdropPath=backdropPath,
        originalLanguage=originalLanguage,
        originalTitle=originalTitle,
        overview=overview,
        posterPath=posterPath,
        releaseDate=releaseDate,
        title=title,
        video=video,
        voteAverage=voteAverage,
        genre=genre,
        bookmark = bookMark,
        id=id,
        category = category
    )
}

fun PersonalityEntity.toPersonality(movies: List<Movies>): Personality{
    return Personality(
        adult = adult,
        gender = gender,
        id = actorId,
        knownFor = movies,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath
    )
}