package com.example.tmdb.presentation.models

import com.example.tmdb.domain.Movies

data class Actor(
    val adult: Boolean,
    val gender: String,
    val id: Int,
    val knownFor: List<Movies>,
    val knownForDepartment: String,
    val name: String,
    val popularity: Double,
    val profilePath: String
)
