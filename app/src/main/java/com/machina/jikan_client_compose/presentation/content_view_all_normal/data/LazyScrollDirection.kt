package com.machina.jikan_client_compose.presentation.content_view_all_normal.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

enum class ScrollDirection {
	UP,
	DOWN
}

private data class LazyScrollDirection(
	private var lastScrollValue: Int,
	private var currentItemIndex: Int,
	private var lastDirection: ScrollDirection
) {
	fun getScrollDirection(
		newScrollValue: Int,
		newItemIndex: Int
	): ScrollDirection {
		lastDirection = when {
			newItemIndex > currentItemIndex -> ScrollDirection.DOWN
			newItemIndex < currentItemIndex -> ScrollDirection.UP
			newScrollValue > lastScrollValue -> ScrollDirection.DOWN
			newScrollValue < lastScrollValue -> ScrollDirection.UP
			else -> lastDirection
		}
		lastScrollValue = newScrollValue
		currentItemIndex = newItemIndex
		return lastDirection
	}
}

@Composable
private fun rememberLazyScrollDirection(
	lastScrollValue: Int = 0,
	currentItemIndex: Int = 0
) = remember {
	LazyScrollDirection(
		lastScrollValue,
		currentItemIndex,
		ScrollDirection.UP
	)
}