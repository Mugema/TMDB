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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val client=OkHttpClient.Builder().addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideApi():ApiService{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
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
}



