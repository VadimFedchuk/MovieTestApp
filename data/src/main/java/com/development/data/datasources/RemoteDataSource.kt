package com.development.data.datasources

import com.development.data.apiService.RemoteApiService
import com.development.data.entities.MoviesResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val remoteApiService: RemoteApiService
) {

    companion object {
        // additional queries
        const val sortByQuery = "primary_release_date.desc"
        const val voteAverageQuery = 7
        const val voteCountQuery = 100
    }
    suspend fun getMoviesFromServer(page: Int): MoviesResponse {
        return remoteApiService.getMovies(
            page = page,
            sortBy = sortByQuery,
            voteAverage = voteAverageQuery,
            voteCount = voteCountQuery
        )
    }
}