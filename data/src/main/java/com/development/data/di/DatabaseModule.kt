package com.development.data.di

import android.content.Context
import androidx.room.Room
import com.development.data.database.MoviesDatabase
import com.development.data.database.MoviesDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "movies_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMoviesDatabaseDao(moviesDatabase: MoviesDatabase): MoviesDatabaseDao {
        return moviesDatabase.moviesDatabaseDao()
    }
}