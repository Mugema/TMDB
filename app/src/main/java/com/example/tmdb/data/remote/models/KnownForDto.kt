package com.example.tmdb.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class KnownForDto(
    val adult: Boolean?=null,
    val backdrop_path: String?=null,
    val genre_ids: List<Int>? =null,
    val id: Int,
    val original_language: String? =null,
    val original_title: String? =null,
    val overview: String? =null,
    val popularity: Double? =null,
    val poster_path: String? =null,
    val release_date: String? =null,
    val title: String? =null,
    val video: Boolean? =null,
    val vote_average: Double? =null,
    val vote_count: Int? =null,
    val first_air_date: String? =null,
    val media_type: String? =null,
    val origin_country: List<String>? =null,
    val name: String? =null,
    val original_name: String? =null,
)