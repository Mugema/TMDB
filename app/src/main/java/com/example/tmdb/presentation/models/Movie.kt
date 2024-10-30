package com.example.tmdb.presentation.models

import com.example.tmdb.domain.Movies

data class Movie(
    val title:String,
    val overview:String,
    val image:String,
    val genre:List<String>,
    val rating:Double,
    val date:String
)

fun Movies.toMovie(): Movie {
    return Movie(
        title= if (originalTitle==title) title
        else "$originalTitle($title)",
        genre = genre,
        image = posterPath,
        overview = overview,
        rating = voteAverage,
        date = releaseDate
    )
}