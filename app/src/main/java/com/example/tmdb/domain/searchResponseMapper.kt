package com.example.tmdb.domain

import com.example.tmdb.presentation.models.Actor
import com.example.tmdb.presentation.models.toMovie

fun Personality.toActor(): Actor{
    return Actor(
        adult = adult,
        gender = if (gender ==2 ) "Male" else if(gender==1) "Female" else "Xx",
        id = id,
        knownFor = knownFor.map { it.toMovie() },
        knownForDepartment = knownForDepartment,
        name = originalName,
        popularity = popularity,
        profilePath = profilePath
    )
}
