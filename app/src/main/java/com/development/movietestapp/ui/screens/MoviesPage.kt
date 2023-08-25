package com.development.movietestapp.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.development.domain.State
import com.development.domain.entities.MovieLocal
import com.development.movietestapp.R
import com.development.movietestapp.ui.listItems.DateViewItem
import com.development.movietestapp.ui.listItems.MovieViewItem
import com.development.movietestapp.ui.theme.DarkBlue
import com.development.movietestapp.ui.views.LoadingView
import com.development.movietestapp.viewModels.MoviesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesPage(viewModel: MoviesViewModel) {
    val moviesPageState = viewModel.uiState.collectAsState()
    val refreshing by remember { mutableStateOf(moviesPageState.value == State.Loading<Any>()) }
    val state = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        viewModel.getMovies(false)
    })
    when (moviesPageState.value) {
        is State.Failure -> FailureStateMoviesPage()
        is State.Loading -> LoadingView()
        is State.Success -> SuccessStateMoviesPage(
            viewModel = viewModel,
            state = state,
            data = (moviesPageState.value as State.Success<List<MovieLocal>>).data,
            refreshing = refreshing
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SuccessStateMoviesPage(
    viewModel: MoviesViewModel,
    state: PullRefreshState,
    data: List<MovieLocal>,
    refreshing: Boolean
) {
    val grouped = data.groupBy { it.publishedAt }
    Box(Modifier.pullRefresh(state)) {
        LazyColumn {
            grouped.forEach { (date, movies) ->
                stickyHeader {
                    DateViewItem(date = date)
                }
                items(movies) {
                    MovieViewItem(model = it) { favorite ->
                        viewModel.changeFavoriteStatus(favorite)
                    }
                }
            }
        }

        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter), backgroundColor = DarkBlue)
    }
}

@Composable
fun FailureStateMoviesPage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.error_text))
    }
}