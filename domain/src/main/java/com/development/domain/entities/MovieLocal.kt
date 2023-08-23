package com.development.domain.entities

data class MovieLocal(
    val id: Int,
    val title: String,
    val description: String,
    val publishedAt: String,
    val urlImage: String,
    val rate: Double,
    var isFavorite: Boolean
)