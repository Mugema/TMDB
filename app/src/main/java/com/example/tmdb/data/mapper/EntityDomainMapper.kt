package com.example.tmdb.data.mapper

import com.example.tmdb.data.local.MoviesEntity
import com.example.tmdb.domain.Movies

fun MoviesEntity.toMovies(genre:List<String>):Movies{
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
        id=id
    )
}