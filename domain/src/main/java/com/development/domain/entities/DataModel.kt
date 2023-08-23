package com.development.domain.entities

sealed class DataModel {
    object LoadingMoreModel : DataModel()
    data class DateModel(val date: String) : DataModel()
    data class MovieModel(val movieLocal: MovieLocal) : DataModel()
}
