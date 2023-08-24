package com.development.data.datasources

import com.development.data.database.MoviesDatabaseDao
import com.development.data.entities.MoviesDbModel
import com.development.domain.entities.MovieLocal
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val moviesDatabaseDao: MoviesDatabaseDao
) {
    suspend fun getMoviesFromDatabase(): List<MoviesDbModel> {
        return moviesDatabaseDao.getMoviesFromDatabase()
    }

   suspend  fun getFavoriteMoviesFromDatabase(): List<MoviesDbModel> {
        return moviesDatabaseDao.getFavoriteMoviesFromDatabase()
    }

    suspend fun setMoviesToDatabase(data: List<MoviesDbModel>) {
        moviesDatabaseDao.saveMovies(data)
    }

    suspend fun updateFavoriteStatus(movie: MovieLocal) {
        moviesDatabaseDao.updateBookmarkById(movie.id, !movie.isFavorite)
    }

    suspend fun deleteAllMovies() {
        moviesDatabaseDao.deleteAll()
    }
}