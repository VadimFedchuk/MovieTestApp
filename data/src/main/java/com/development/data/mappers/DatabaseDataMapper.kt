package com.development.data.mappers

import com.development.data.entities.MoviesDbModel
import com.development.domain.entities.MovieLocal
import javax.inject.Inject

class DatabaseDataMapper @Inject constructor() {

    fun toMovieLocal(movieDbModel: MoviesDbModel): MovieLocal {
        return MovieLocal(
            id = movieDbModel.id,
            title = movieDbModel.title,
            description = movieDbModel.description,
            rate = movieDbModel.rate,
            publishedAt = movieDbModel.publishedAt,
            urlImage = movieDbModel.urlToImage,
            isFavorite = movieDbModel.isFavorite
        )
    }
}