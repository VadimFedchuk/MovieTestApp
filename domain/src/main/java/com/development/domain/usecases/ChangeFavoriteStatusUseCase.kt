package com.development.domain.usecases

import com.development.domain.entities.MovieLocal
import com.development.domain.respositories.MoviesRepository
import javax.inject.Inject

class ChangeFavoriteStatusUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movie: MovieLocal): List<MovieLocal> {
        return repository.changeFavoriteStatusMovie(movie)
    }
}