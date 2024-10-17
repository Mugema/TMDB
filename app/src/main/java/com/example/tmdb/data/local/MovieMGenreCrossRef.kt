package com.example.tmdb.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["genreId","id"])
data class MovieMGenreCrossRef(
    val genreId:Int,
    val id:Int
)

data class GenreForMovie(
    @Embedded val movie:MoviesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genreId",
        associateBy = Junction(MovieMGenreCrossRef::class)
    )
    val genreList:List<MGenreEntity>
)

data class MoviesWithGenre(
    @Embedded val genre:MGenreEntity,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "id",
        associateBy = Junction(MovieMGenreCrossRef::class)
    )
    val movieList:List<MoviesEntity>
)
