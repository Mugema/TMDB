package com.example.tmdb.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Routes{
    @Serializable
    object Login

    @Serializable
    object SignUp

    @Serializable
    object Discover

    @Serializable
    object Settings

    @Serializable
    object Authorization

    @Serializable
    object Landing

    @Serializable
    object BookMarkedScreen

    @Serializable
    data class MovieDetailsScreen(val movieId:Int)

    @Serializable
    data class SearchResults(val query:String)
}