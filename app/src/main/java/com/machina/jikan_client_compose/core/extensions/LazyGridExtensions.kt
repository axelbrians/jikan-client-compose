package com.machina.jikan_client_compose.core.extensions

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.*
import com.machina.jikan_client_compose.presentation.content_view_all_screen.data.ScrollDirection

fun LazyGridState.isScrolledToTheEnd(): Boolean {
	return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
fun LazyGridState.isScrollingUp(): Boolean {
	var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
	var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
	var isScrollingUpPreviously by remember(this) { mutableStateOf(true) }
	return remember(this) {
		derivedStateOf {
			isScrollingUpPreviously = when {
				firstVisibleItemIndex > previousIndex -> false
				firstVisibleItemIndex < previousIndex -> true
				firstVisibleItemScrollOffset > previousScrollOffset -> false
				firstVisibleItemScrollOffset < previousScrollOffset -> true
				else -> isScrollingUpPreviously
			}
			previousIndex = firstVisibleItemIndex
			previousScrollOffset = firstVisibleItemScrollOffset
			isScrollingUpPreviously
		}
	}.value
}

@Composable
fun LazyGridState.scrollDirection(): ScrollDirection {
	var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
	var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
	var previousDirection by remember(this) { mutableStateOf(ScrollDirection.UP) }
	return remember(this) {
		derivedStateOf {
			previousDirection = when {
				firstVisibleItemIndex > previousIndex -> ScrollDirection.DOWN
				firstVisibleItemIndex < previousIndex -> ScrollDirection.UP
				firstVisibleItemScrollOffset > previousScrollOffset -> ScrollDirection.DOWN
				firstVisibleItemScrollOffset < previousScrollOffset -> ScrollDirection.UP
				else -> previousDirection
			}
			previousIndex = firstVisibleItemIndex
			previousScrollOffset = firstVisibleItemScrollOffset
			previousDirection
		}
	}.value
}