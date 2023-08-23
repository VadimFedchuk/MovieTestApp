package com.development.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesDbModel(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val rate: Double,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
)