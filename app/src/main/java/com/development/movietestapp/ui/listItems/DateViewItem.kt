package com.development.movietestapp.ui.listItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateViewItem(date: String) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            date,
            style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        )
    }
}