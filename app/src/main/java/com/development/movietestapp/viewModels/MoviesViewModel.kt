package com.development.movietestapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.domain.State
import com.development.domain.entities.MovieLocal
import com.development.domain.usecases.ChangeFavoriteStatusUseCase
import com.development.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<State<MutableList<MovieLocal>>>(State.Loading())
    val uiState = _uiState.asStateFlow()

    private var page = 1

    init {
        getMovies(false)
    }

    fun getMovies(loadMore: Boolean) {
        viewModelScope.launch {
            if (loadMore) ++page else page = 1
            val previousData =
                if (loadMore && _uiState.value is State.Success)
                        (_uiState.value as State.Success<List<MovieLocal>>).data else emptyList()
            val dataState = getMoviesUseCase.invoke(page = page)
            if (dataState is State.Success) {
                val result = dataState.data + previousData
                _uiState.tryEmit(State.Success(result.toMutableList()))
            } else if (dataState is State.Failure && previousData.isNotEmpty()) {
                _uiState.tryEmit(State.Success(previousData.toMutableList()))
            } else {
                _uiState.tryEmit(dataState)
            }
        }
    }

    fun loadMoreMovies() {
        viewModelScope.launch {
            if (_uiState.value != State.Loading<Any>()) {
                getMovies(loadMore = true)
            }
        }
    }

    fun changeFavoriteStatus(movie: MovieLocal) {
        viewModelScope.launch {
            val favoriteList = changeFavoriteStatusUseCase.invoke(movie)
            val previousData =
                if (_uiState.value is State.Success) (_uiState.value as State.Success<List<MovieLocal>>).data.toMutableList() else mutableListOf()
            for (mainMovie in previousData) {
                val matchingFavorite = favoriteList.find { it.id == mainMovie.id }
                previousData[previousData.indexOf(mainMovie)] =
                    mainMovie.copy(isFavorite = matchingFavorite?.isFavorite ?: false)
            }
            _uiState.tryEmit(State.Success(previousData.toMutableList()))
        }
    }
}
