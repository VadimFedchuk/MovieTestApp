package com.development.domain.respositories

import com.development.domain.entities.MovieLocal

interface MoviesRepository {
    suspend fun getMovies(page: Int): List<MovieLocal>
    suspend fun getFavoriteMovies(): List<MovieLocal>
    suspend fun changeFavoriteStatusMovie(movie: MovieLocal): List<MovieLocal>
}