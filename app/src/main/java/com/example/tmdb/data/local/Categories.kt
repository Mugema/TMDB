package com.example.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Categories(
    @PrimaryKey(autoGenerate = false) val categoryId:Int,
    val categoryName:String
)
