package com.example.tmdb.presentation.discoverScreen

sealed interface Actions{
    data class onChipClicked(val chipName:String):Actions
    data class onSearch(val query:String):Actions
}