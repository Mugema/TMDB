package com.example.tmdb.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class ResultActorDto(
    val adult: Boolean,
    val gender: Int ,
    val id: Int,
    val known_for: List<KnownForDto> ?=null,
    val known_for_department: String ?=null,
    val name: String?=null,
    val original_name: String?=null,
    val popularity: Double,
    val profile_path: String?=null
)