package com.development.data.entities

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results") val movieRemotes: List<MovieRemote>?
)

data class MovieRemote(
    val id: Int,
    @SerializedName("original_title") val title: String,
    @SerializedName("overview") val description: String,
    @SerializedName("popularity") val rate: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String
)
