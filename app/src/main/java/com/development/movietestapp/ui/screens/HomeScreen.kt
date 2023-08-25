package com.development.movietestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.development.movietestapp.R
import com.development.movietestapp.ui.theme.DarkBlue
import com.development.movietestapp.ui.theme.MainBackgroundColor
import com.development.movietestapp.ui.views.CircleImage
import com.development.movietestapp.ui.views.Tabs
import com.development.movietestapp.utils.PreferencesManager
import com.development.movietestapp.utils.USER_AVATAR_KEY
import com.development.movietestapp.viewModels.MoviesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val tabs = listOf(R.string.films, R.string.favorites)
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val pagerState = rememberPagerState(pageCount = tabs.size)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopContainer(
            avatarUrl = preferencesManager.getData(USER_AVATAR_KEY, ""),
            tabs = tabs,
            pagerState = pagerState
        )
        TabsContent(pagerState = pagerState, viewModel = moviesViewModel)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TopContainer(avatarUrl: String, tabs: List<Int>, pagerState: PagerState) {
    Box(
        modifier = Modifier
            .height(126.dp)
            .background(DarkBlue)

    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                CircleImage(imageUrl = avatarUrl)
            }
            Tabs(data = tabs, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(pagerState: PagerState, viewModel: MoviesViewModel) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackgroundColor)
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        when (it) {
            0 -> MoviesPage(viewModel = viewModel)
            1 -> FavoriteMoviesPage(viewModel = viewModel)
        }
    }
}
