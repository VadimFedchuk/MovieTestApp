package com.development.domain.usecases

import com.development.domain.State
import com.development.domain.entities.MovieLocal
import com.development.domain.respositories.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(page: Int): State<List<MovieLocal>> {
        val data = repository.getMovies(page)
        return if (data.isEmpty()) {
            State.Failure()
        } else {
            State.Success(data)
        }
    }
}