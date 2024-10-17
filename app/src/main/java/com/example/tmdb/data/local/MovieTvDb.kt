package com.example.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([
    MoviesEntity::class,
    MovieMGenreCrossRef::class,
    MovieCategoryCrossRef::class,
    MGenreEntity::class,
    Categories::class,], version = 1)
abstract class MovieTvDb:RoomDatabase() {

    abstract fun getMovieDao():MoviesDao
    abstract fun getGenreDao():GenreDao

}