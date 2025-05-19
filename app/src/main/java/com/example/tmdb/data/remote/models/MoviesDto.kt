package com.example.tmdb.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class MoviesDto(
    val dates: Dates?=null,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)