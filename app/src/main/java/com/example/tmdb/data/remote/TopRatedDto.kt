package com.example.tmdb.data.remote

data class TopRatedDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)