package com.machina.jikan_client_compose.core.extensions

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.machina.jikan_client_compose.presentation.content_view_all_normal.data.ScrollDirection

@Composable
fun LazyListState.isScrolledToTheEnd(): State<Boolean> {
	val isScrolledToEnd = remember {
		derivedStateOf {
			layoutInfo.visibleItemsInfo.lastOrNull()?.index ==
			  layoutInfo.totalItemsCount - 1
		}
	}

	return isScrolledToEnd
}

@Composable
fun LazyListState.isScrolledToTheFirst(): State<Boolean> {
	val isScrolledToTheFirst = remember {
		derivedStateOf {
			firstVisibleItemIndex == 0 &&
			  firstVisibleItemScrollOffset == 0
		}
	}
	return isScrolledToTheFirst
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
fun LazyListState.isScrollingUp(): Boolean {
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
fun LazyListState.scrollDirection(): ScrollDirection {
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