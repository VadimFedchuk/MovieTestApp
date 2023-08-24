package com.development.movietestapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.development.domain.State
import com.development.domain.entities.MovieLocal
import com.development.movietestapp.R
import com.development.movietestapp.ui.listItems.DateViewItem
import com.development.movietestapp.ui.listItems.MovieViewItem
import com.development.movietestapp.ui.views.LoadingView
import com.development.movietestapp.viewModels.MoviesViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteMoviesPage(viewModel: MoviesViewModel) {
    val moviesPageState = viewModel.uiState.collectAsState()
    when (moviesPageState.value) {
        is State.Failure -> FailureStateMoviesPage()
        is State.Loading -> LoadingView()
        is State.Success -> {
            val filtered = (moviesPageState.value as State.Success<List<MovieLocal>>).data.filter { it.isFavorite }
            if (filtered.isNotEmpty()) {
                val grouped = filtered.groupBy { it.publishedAt }
                LazyColumn {
                    grouped.forEach { (date, movies) ->
                        stickyHeader {
                            DateViewItem(date = date)
                        }
                        items(movies) { it ->
                            MovieViewItem(model = it) { favorite->
                                viewModel.changeFavoriteStatus(favorite)
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(id = R.string.favorite_is_empty))
                }
            }
        }
    }
}