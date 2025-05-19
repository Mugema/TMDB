package com.example.tmdb.presentation.discoverScreen

import com.example.tmdb.domain.Category

sealed interface DiscoverScreenIntents{
    data class OnChipClicked(val category: Category):DiscoverScreenIntents
    data class OnSearch(val query:String):DiscoverScreenIntents
    data class OnPosterClicked(val movieId:Int): DiscoverScreenIntents
    data class OnTabClicked(val screen:Int): DiscoverScreenIntents
    data object OnProfilePictureClicked: DiscoverScreenIntents
}