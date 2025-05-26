package com.example.tmdb.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["id","knownFor"])
data class PersonalityEntity(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownFor: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String
)
