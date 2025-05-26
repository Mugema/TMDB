package com.example.tmdb.data.remote.models

data class ResultActorDto(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for: List<KnownForDto>,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
)