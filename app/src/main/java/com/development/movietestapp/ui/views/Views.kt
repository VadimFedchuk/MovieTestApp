package com.development.movietestapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.development.movietestapp.ui.theme.DarkBlue
import com.development.movietestapp.ui.theme.DisActiveTabColorText
import com.development.movietestapp.utils.IMAGE_SIZE
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@Composable
fun CircleImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .padding(end = 16.dp, top = 16.dp)
            .clip(CircleShape)
            .size(IMAGE_SIZE.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "avatar",
            contentScale = ContentScale.Fit,
        )
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(data: List<Int>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        backgroundColor = DarkBlue,
        selectedTabIndex = pagerState.pageCount,
        divider = { },
        indicator = {
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(it[pagerState.currentPage]),
                color = DisActiveTabColorText
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        data.forEachIndexed { index, titleResourceId ->
            val colorText =
                if (pagerState.currentPage == index) Color.White else DisActiveTabColorText

            Tab(selected = pagerState.currentPage == index,
                modifier = Modifier
                    .background(DarkBlue)
                    .padding(vertical = 16.dp),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }) {
                Text(
                    text = stringResource(id = titleResourceId).uppercase(),
                    style = TextStyle(color = colorText),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                )
            }
        }
    }
}

@Composable
fun LoadingView() {
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = DarkBlue)
    }
}