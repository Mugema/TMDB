package com.example.tmdb.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb.data.local.MovieTvDb
import com.example.tmdb.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi():ApiService{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context):MovieTvDb{
        return synchronized(this){
            Room.databaseBuilder(context,MovieTvDb::class.java,"Tmdb.db")
                .build()
        }
    }

    @Singleton
    @Provides
    fun providesIODispatcher():CoroutineDispatcher{
        return Dispatchers.IO
    }
}



