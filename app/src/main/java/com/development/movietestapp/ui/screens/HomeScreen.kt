package com.development.movietestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.development.domain.entities.DataModel
import com.development.domain.entities.MovieLocal
import com.development.movietestapp.R
import com.development.movietestapp.ui.listItems.DateViewItem
import com.development.movietestapp.ui.listItems.LoadingViewItem
import com.development.movietestapp.ui.listItems.MovieViewItem
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
        TabsContent(pagerState = pagerState)
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
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackgroundColor),
        verticalAlignment = Alignment.Top
    ) {
        LazyColumn() {
            items<DataModel>(testList()) { item ->
                when (item) {
                    is DataModel.DateModel -> {
                        DateViewItem(date = item.date)
                    }

                    is DataModel.MovieModel -> {
                        MovieViewItem(model = item.movieLocal)
                    }

                    is DataModel.LoadingMoreModel -> {
                        LoadingViewItem()
                    }
                }
            }
        }
    }
}

fun testList(): List<DataModel> {
    return listOf(
        DataModel.DateModel("feb 2021"),
        DataModel.MovieModel(
            MovieLocal(
                1,
                "title",
                "American neo-noir black comedy crime film written and directed by Quentin Tarantino",
                "puasd",
                "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png",
                5.0,
                false
            )
        ),
        DataModel.MovieModel(
            MovieLocal(
                2,
                "title",
                "American neo-noir black comedy crime film written and directed by Quentin Tarantino",
                "puasd",
                "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png",
                5.0,
                true
            )
        ),
        DataModel.LoadingMoreModel
    )
}
