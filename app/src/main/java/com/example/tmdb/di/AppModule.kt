package com.example.tmdb.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb.data.local.MovieDb
import com.example.tmdb.data.local.MoviesLocalDataSource
import com.example.tmdb.data.local.dao.CategoryDao
import com.example.tmdb.data.local.dao.GenreDao
import com.example.tmdb.data.local.dao.MoviesDao
import com.example.tmdb.data.local.dao.PersonalityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context):MovieDb{
        return synchronized(this){
            Room.databaseBuilder(context,MovieDb::class.java,"Tmdb.db")
                .build()
        }
    }

    @Singleton
    @Provides
    fun providesIODispatcher():CoroutineDispatcher{
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun providesMoviesLocalDataSource(
        dispatcher: CoroutineDispatcher,
        categoryDao: CategoryDao,
        movieDao: MoviesDao,
        genreDao: GenreDao,
        personalityDao: PersonalityDao
    ): MoviesLocalDataSource {
        return MoviesLocalDataSource(
            ioDispatcher =  dispatcher,
            categoryDao = categoryDao,
            movieDao = movieDao,
            genreDao = genreDao,
            personalityDao = personalityDao
        )
    }

    @Provides
    @Singleton
    fun providesGenreDao(db: MovieDb): GenreDao{
        return db.getGenreDao()
    }

    @Provides
    @Singleton
    fun providesCategoryDao(db: MovieDb): CategoryDao {
        return db.getCategoryDao()
    }

    @Provides
    @Singleton
    fun providesMovieDao(db: MovieDb): MoviesDao {
        return db.getMovieDao()
    }

    @Singleton
    @Provides
    fun providesPersonalityDao(db: MovieDb): PersonalityDao{
        return db.getPersonalityDao()
    }

    @Singleton
    @Provides
    fun providesKtorClient(): HttpClient{
        return HttpClient(Android ){
            install(ContentNegotiation){
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging){
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
}



