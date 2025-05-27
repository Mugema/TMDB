package com.example.tmdb.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class ActorDto(
    val page: Int,
    val results: List<ResultActorDto>,
    val total_pages: Int,
    val total_results: Int
)