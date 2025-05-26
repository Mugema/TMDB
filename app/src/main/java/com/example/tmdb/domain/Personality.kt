package com.example.tmdb.domain

data class Personality(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownFor: List<Movies>,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String
)
