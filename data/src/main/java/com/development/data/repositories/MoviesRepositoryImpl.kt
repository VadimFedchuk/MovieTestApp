package com.development.data.repositories

import android.util.Log
import com.development.data.datasources.LocalDataSource
import com.development.data.datasources.RemoteDataSource
import com.development.data.entities.MoviesDbModel
import com.development.data.mappers.DatabaseDataMapper
import com.development.data.mappers.ResponseDataMapper
import com.development.domain.entities.MovieLocal
import com.development.domain.respositories.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val responseDataMapper: ResponseDataMapper,
    private val databaseDataMapper: DatabaseDataMapper
): MoviesRepository {

    override suspend fun getMovies(page: Int): List<MovieLocal> {
        return try {
            val remoteData = remoteDataSource.getMoviesFromServer(page = page)
            val convertedRemoteData: List<MoviesDbModel> = responseDataMapper.toNewsModel(remoteData)
            localDataSource.setMoviesToDatabase(convertedRemoteData)
            convertedRemoteData.map { databaseDataMapper.toMovieLocal(it) }.toList()
        } catch (e: Exception) {
            val localSavedData = localDataSource.getMoviesFromDatabase()
            if (localSavedData.isEmpty()) {
                emptyList()
            } else {
                localSavedData.map { databaseDataMapper.toMovieLocal(it) }.toList()
            }
        }
    }

    override suspend fun getFavoriteMovies(): List<MovieLocal> {
        return localDataSource.getFavoriteMoviesFromDatabase().map {
            databaseDataMapper.toMovieLocal(it) }.toList()
    }

    override suspend fun changeFavoriteStatusMovie(movie: MovieLocal):List<MovieLocal> {
        localDataSource.updateFavoriteStatus(movie)
        return getFavoriteMovies()
    }
}