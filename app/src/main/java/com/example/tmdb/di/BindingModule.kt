package com.example.tmdb.di

import com.example.tmdb.data.remote.APIServiceImp
import com.example.tmdb.data.remote.ApiService
import com.example.tmdb.data.repository.MoviesRepositoryImpl
import com.example.tmdb.data.repository.TMDBRepositoryImpl
import com.example.tmdb.domain.repository.MoviesRepository
import com.example.tmdb.domain.repository.TMDBRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {

    @Binds
    @Singleton
    abstract fun BindsTMDBRepository(TmdbRepositoryImp:TMDBRepositoryImpl):TMDBRepository

    @Binds
    @Singleton
    abstract fun BindsMoviesRepository( moviesRepository: MoviesRepositoryImpl ) : MoviesRepository

    @Binds
    @Singleton
    abstract fun BindsApiService(apiServiceImpl: APIServiceImp) : ApiService
}