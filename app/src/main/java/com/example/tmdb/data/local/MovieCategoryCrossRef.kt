package com.example.tmdb.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["categoryId","id"])
data class MovieCategoryCrossRef(
    val categoryId:Int,
    val id:Int
)

data class MovieCategory(
    @Embedded val movie:MoviesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId",
        associateBy = Junction(MovieCategoryCrossRef::class)
    )
    val category:List<Categories>
)

data class CategoryMovie(
    @Embedded val category:Categories,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id",
        associateBy = Junction(MovieCategoryCrossRef::class)
    )
    val movie:List<MoviesEntity>
)