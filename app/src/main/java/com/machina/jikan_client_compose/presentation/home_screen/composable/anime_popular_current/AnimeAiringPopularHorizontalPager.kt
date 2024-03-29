package com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class, coil.annotation.ExperimentalCoilApi::class)
@Composable
fun AnimeHeadlineCarousel(
	dataSet: List<AnimeThumbnail>,
	modifier: Modifier = Modifier,
	pagerState: PagerState = rememberPagerState(),
	onClick: (Int, ContentType) -> Unit
) {
	Box(modifier = modifier.fillMaxWidth()) {
		HorizontalPager(
			modifier = Modifier
				.fillMaxWidth()
				.height(280.dp),
			state = pagerState,
			count = dataSet.size,
			itemSpacing = 12.dp,
			contentPadding = PaddingValues(horizontal = 48.dp),
			key = { dataSet[it].malId }
		) { index ->
			ItemHeadlineCarousel(
				modifier = Modifier,
				data = dataSet[index],
				currentPage = index,
				onClick = { onClick(dataSet[index].malId, ContentType.Anime) }
			)
		}

		/* horizontal chip pager indicator */
		Row(
			modifier = Modifier.align(Alignment.BottomCenter),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			repeat(dataSet.size) {
				val targetPageOffset = (
					pagerState.currentPage + pagerState.currentPageOffset - pagerState.targetPage
					).absoluteValue

				HorizontalPagerChipIndicator(
					itemIndex = it,
					currentPage = pagerState.currentPage,
					targetPage = pagerState.targetPage,
					currentPageOffset = pagerState.currentPageOffset.absoluteValue,
					targetPageOffset = targetPageOffset
				)
			}
		}
		/* End Of horizontal chip pager indicator */
	}
}

@OptIn(ExperimentalPagerApi::class, coil.annotation.ExperimentalCoilApi::class)
@Composable
fun AnimeAiringPopularHorizontalPager(
	modifier: Modifier = Modifier,
	dataSet: StateListWrapper<AnimeAiringPopular> = StateListWrapper(),
	pagerState: PagerState = rememberPagerState(),
	shimmerInstance: Shimmer,
	navigateToContentDetailsScreen: (Int, ContentType) -> Unit
) {
	val (data, isLoading, error) = dataSet
	val shownCount = if (isLoading) 3 else data.size

	Box(modifier = modifier.fillMaxWidth()) {
		HorizontalPager(
			modifier = Modifier
				.fillMaxWidth()
				.height(280.dp),
			state = pagerState,
			count = shownCount,
			itemSpacing = 12.dp,
			contentPadding = PaddingValues(horizontal = 48.dp),
			key = { page -> if (isLoading) page else data[page].malId }
		) { page ->
			if (isLoading) {
				PagerItemAnimeAiringPopularShimmer(
					shimmerInstance = shimmerInstance,
					currentPage = page
				)
			} else {
				PagerItemAnimeAiringPopular(
					modifier = Modifier,
					data = data[page],
					currentPage = page,
					onClick = { navigateToContentDetailsScreen(data[page].malId, ContentType.Anime) }
				)
			}
		}

		/* horizontal chip pager indicator */
		Row(
			modifier = Modifier.align(Alignment.BottomCenter),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			if (isLoading) {
				HorizontalPagerChipIndicatorShimmer(shimmerInstance)
			} else {
				repeat(data.size) {
					val targetPageOffset = (
						pagerState.currentPage + pagerState.currentPageOffset - pagerState.targetPage
					).absoluteValue

					HorizontalPagerChipIndicator(
						itemIndex = it,
						currentPage = pagerState.currentPage,
						targetPage = pagerState.targetPage,
						currentPageOffset = pagerState.currentPageOffset.absoluteValue,
						targetPageOffset = targetPageOffset
					)
				}
			}
		}
		/* End Of horizontal chip pager indicator */
	}
}
