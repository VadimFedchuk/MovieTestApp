package com.development.domain

sealed class State<out D> {
    class Loading<out T> : State<T>()
    class LoadingMore<out T> : State<T>()
    data class Success<out T>(val data: T) : State<T>()
    class Failure<out T> : State<T>()
}