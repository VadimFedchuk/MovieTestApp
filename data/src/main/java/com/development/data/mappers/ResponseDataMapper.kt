package com.development.data.mappers

import com.development.data.BuildConfig
import com.development.data.entities.MovieRemote
import com.development.data.entities.MoviesDbModel
import com.development.data.entities.MoviesResponse
import javax.inject.Inject

class ResponseDataMapper @Inject constructor() {
    fun toNewsModel(response: MoviesResponse): List<MoviesDbModel> {
        return response.movieRemotes?.map { parseMovie(it) }?.toList() ?: arrayListOf()
    }

    private fun parseMovie(movieRemote: MovieRemote): MoviesDbModel {
        return MoviesDbModel(
            id = movieRemote.id,
            title = movieRemote.title,
            description = movieRemote.description,
            rate = movieRemote.rate,
            publishedAt = movieRemote.releaseDate,
            urlToImage = BuildConfig.BASE_IMAGE_URL + movieRemote.posterPath,
            isFavorite = false
        )
    }
}