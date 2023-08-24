package com.development.data.di

import com.development.data.repositories.MoviesRepositoryImpl
import com.development.domain.respositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(repository: MoviesRepositoryImpl): MoviesRepository {
        return repository
    }
}