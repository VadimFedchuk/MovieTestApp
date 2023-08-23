package com.development.movietestapp.ui.listItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.development.movietestapp.ui.theme.DarkBlue

@Composable
fun LoadingViewItem() {
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = DarkBlue)
    }
}