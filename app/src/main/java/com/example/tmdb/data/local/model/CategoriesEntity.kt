package com.example.tmdb.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["categoryId","movieId"])
data class CategoriesEntity(
    val categoryId:Int,
    val movieId:Int,
)
