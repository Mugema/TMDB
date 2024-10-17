package com.example.tmdb.di

import com.example.tmdb.data.repository.TMDBRepositoryImpl
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
}