package com.development.data.apiService

import com.development.data.entities.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApiService {

    // "https://api.themoviedb.org/3/discover/movie?page=1&sort_by=primary_release_date.desc&vote_average.gte=7&vote_count.gte=100
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("vote_average.gte") voteAverage: Int,
        @Query("vote_count.gte") voteCount: Int,
    ) : MoviesResponse
}