package com.example.tmdb.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tmdb.data.local.model.CategoriesEntity

@Dao
interface CategoryDao {
    @Upsert
    suspend fun addCategory(category: CategoriesEntity)

    @Query("Select * from CategoriesEntity where movieId=:id")
    suspend fun selectCategoriesForId(id:Int): List<CategoriesEntity>
}