package com.example.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.data.local.dao.CategoryDao
import com.example.tmdb.data.local.dao.GenreDao
import com.example.tmdb.data.local.dao.MoviesDao
import com.example.tmdb.data.local.dao.PersonalityDao
import com.example.tmdb.data.local.model.CategoriesEntity
import com.example.tmdb.data.local.model.GenreEntity
import com.example.tmdb.data.local.model.MoviesEntity
import com.example.tmdb.data.local.model.PersonalityEntity

@Database(
    [ MoviesEntity::class,CategoriesEntity::class,GenreEntity::class, PersonalityEntity::class],
    version = 1
)
abstract class MovieDb:RoomDatabase() {

    abstract fun getMovieDao(): MoviesDao
    abstract fun getGenreDao(): GenreDao
    abstract fun getCategoryDao():CategoryDao
    abstract fun getPersonalityDao(): PersonalityDao

}