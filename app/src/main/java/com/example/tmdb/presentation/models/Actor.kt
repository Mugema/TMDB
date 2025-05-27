package com.example.tmdb.presentation.models

data class Actor(
    val adult: Boolean,
    val gender: String,
    val id: Int,
    val knownFor: List<Movie>,
    val knownForDepartment: String,
    val name: String,
    val popularity: Double,
    val profilePath: String
)
