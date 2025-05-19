package com.example.tmdb.domain

fun resolveCategory(category: Category):Int{
    return when(category){
        Category.NowPlaying -> 1
        Category.UpComing -> 2
        Category.TopRated -> 3
        Category.Popular -> 4
    }
}