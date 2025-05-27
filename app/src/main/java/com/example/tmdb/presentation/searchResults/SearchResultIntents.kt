package com.example.tmdb.presentation.searchResults

sealed interface SearchResultIntents {
    data class ChipClicked(val chip: Showing): SearchResultIntents
    data class ActorClicked(val id:Int): SearchResultIntents
}