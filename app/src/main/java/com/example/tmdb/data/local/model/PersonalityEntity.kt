package com.example.tmdb.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["actorId","knownFor"])
data class PersonalityEntity(
    val adult: Boolean,
    val gender: Int,
    val actorId: Int,
    val knownFor: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String
)
