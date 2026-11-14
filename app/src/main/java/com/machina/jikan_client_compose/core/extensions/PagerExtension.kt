package com.machina.jikan_client_compose.core.extensions

import androidx.compose.foundation.pager.PagerState

/**
 * Signed distance of [page] from the current scroll position, in pages.
 * Replacement for Accompanist's `PagerScope.calculateCurrentOffsetForPage`.
 */
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
	return (currentPage - page) + currentPageOffsetFraction
}
