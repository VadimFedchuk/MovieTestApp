package com.development.movietestapp.utils

import androidx.compose.foundation.lazy.LazyListState
import java.text.SimpleDateFormat
import java.util.Locale

fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun String?.isSecondDateBeforeFirst(secondDate: String): Boolean {
    if (this == null) return false
    return try {
        val outputFormatter = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)

        val first = outputFormatter.parse(this)
        val second = outputFormatter.parse(secondDate)
        val comparisonResult = first.compareTo(second)
        return when {
            comparisonResult < 0 -> false
            comparisonResult > 0 -> true
            else -> false
        }
    } catch (e:Exception) {
        false
    }
}