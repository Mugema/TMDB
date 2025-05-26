package com.example.tmdb.presentation.models

import com.example.tmdb.domain.Movies
import com.example.tmdb.domain.mapGenre


data class Movie(
    val title:String="N/A",
    val overview:String="N/A",
    val image:String="N/A",
    val backDrop:String = "N/A",
    val genre:List<String> = listOf("N/A"),
    val rating:Double=0.0,
    val date:String="N/A",
    val bookmark:Boolean=false,
    val id:Int=0,
    val category: List<Int> = mutableListOf<Int>(),
    val adult: String = "18+",
    val originalLanguage: String = "ENG"
)

fun Movies.toMovie(): Movie {
    return Movie(
        title= if (originalTitle==title) title else "$originalTitle($title)",
        overview = overview,
        genre = mapGenre(genre),
        backDrop = backdropPath,
        image = posterPath,
        rating = voteAverage,
        date = releaseDate.substring(0..3),
        bookmark = bookmark,
        id=id,
        category = category,
        adult = "18+",
        originalLanguage = originalLanguage
    )
}
