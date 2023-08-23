package com.development.data.datasources

import com.development.data.database.MoviesDatabaseDao
import com.development.data.entities.MoviesDbModel
import com.development.domain.entities.MovieLocal
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val moviesDatabaseDao: MoviesDatabaseDao
) {
    fun getMoviesFromDatabase(): List<MoviesDbModel> {
        return moviesDatabaseDao.getMoviesFromDatabase()
    }

    fun getFavoriteMoviesFromDatabase(): List<MoviesDbModel> {
        return moviesDatabaseDao.getFavoriteMoviesFromDatabase()
    }

    fun setMoviesToDatabase(data: List<MoviesDbModel>) {
        moviesDatabaseDao.saveMovies(data)
    }

    fun updateFavoriteStatus(movie: MovieLocal) {
        moviesDatabaseDao.updateBookmarkById(movie.id, !movie.isFavorite)
    }

    fun deleteAllMovies() {
        moviesDatabaseDao.deleteAll()
    }
}