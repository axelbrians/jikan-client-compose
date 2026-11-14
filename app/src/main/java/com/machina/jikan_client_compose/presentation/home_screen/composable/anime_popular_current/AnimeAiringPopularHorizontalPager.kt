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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import kotlin.math.absoluteValue

@OptIn(coil3.annotation.ExperimentalCoilApi::class)
@Composable
fun AnimeHeadlineCarousel(
	dataSet: List<AnimeThumbnail>,
	modifier: Modifier = Modifier,
	pagerState: PagerState = rememberPagerState { dataSet.size },
	onClick: (Int, ContentType) -> Unit
) {
	Box(modifier = modifier.fillMaxWidth()) {
		HorizontalPager(
			modifier = Modifier
				.fillMaxWidth()
				.height(280.dp),
			state = pagerState,
						pageSpacing = 12.dp,
			contentPadding = PaddingValues(horizontal = 48.dp),
			key = { dataSet[it].malId }
		) { index ->
			ItemHeadlineCarousel(
				pagerState = pagerState,
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
					pagerState.currentPage + pagerState.currentPageOffsetFraction - pagerState.targetPage
					).absoluteValue

				HorizontalPagerChipIndicator(
					itemIndex = it,
					currentPage = pagerState.currentPage,
					targetPage = pagerState.targetPage,
					currentPageOffset = pagerState.currentPageOffsetFraction.absoluteValue,
					targetPageOffset = targetPageOffset
				)
			}
		}
		/* End Of horizontal chip pager indicator */
	}
}

@OptIn(coil3.annotation.ExperimentalCoilApi::class)
@Composable
fun AnimeAiringPopularHorizontalPager(
	modifier: Modifier = Modifier,
	dataSet: StateListWrapper<AnimeAiringPopular> = StateListWrapper(),
	pagerState: PagerState = rememberPagerState { if (dataSet.isLoading) 3 else dataSet.data.size },
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
						pageSpacing = 12.dp,
			contentPadding = PaddingValues(horizontal = 48.dp),
			key = { page -> if (isLoading) page else data[page].malId }
		) { page ->
			if (isLoading) {
				PagerItemAnimeAiringPopularShimmer(
					pagerState = pagerState,
					shimmerInstance = shimmerInstance,
					currentPage = page
				)
			} else {
				PagerItemAnimeAiringPopular(
					pagerState = pagerState,
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
						pagerState.currentPage + pagerState.currentPageOffsetFraction - pagerState.targetPage
					).absoluteValue

					HorizontalPagerChipIndicator(
						itemIndex = it,
						currentPage = pagerState.currentPage,
						targetPage = pagerState.targetPage,
						currentPageOffset = pagerState.currentPageOffsetFraction.absoluteValue,
						targetPageOffset = targetPageOffset
					)
				}
			}
		}
		/* End Of horizontal chip pager indicator */
	}
}
