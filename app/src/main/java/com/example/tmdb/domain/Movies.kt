package com.example.tmdb.domain

data class Movies(
    val id:Int,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val genre:List<String>,
    val bookmark:Boolean
)
