package com.development.data.mappers

import com.development.data.BuildConfig
import com.development.data.entities.MovieRemote
import com.development.data.entities.MoviesDbModel
import com.development.data.entities.MoviesResponse
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ResponseDataMapper @Inject constructor() {
    fun toMoviesDbModel(response: MoviesResponse): List<MoviesDbModel> {
        return response.movieRemotes?.map { parseMovie(it) }?.toList() ?: arrayListOf()
    }

    private fun parseMovie(movieRemote: MovieRemote): MoviesDbModel {
        return MoviesDbModel(
            id = movieRemote.id,
            title = movieRemote.title,
            description = movieRemote.description,
            rate = String.format("%.1f", movieRemote.rate).toDouble(),
            publishedAt = formatDate(movieRemote.releaseDate),
            urlToImage = BuildConfig.BASE_IMAGE_URL + movieRemote.posterPath,
            isFavorite = false
        )
    }

    private fun formatDate(inputDate: String): String {
        return try {
            val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val outputFormatter = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)

            val date = inputFormatter.parse(inputDate)
            outputFormatter.format(date)
        } catch (_:Exception) {
            ""
        }
    }
}