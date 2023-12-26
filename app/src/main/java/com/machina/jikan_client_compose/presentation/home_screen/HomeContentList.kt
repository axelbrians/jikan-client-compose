package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.core.extensions.isScrolledToTheFirst
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.domain.use_case.anime.SectionType
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.composable.pull_to_refresh.GlowingBeamLoadingIndicator
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.AnimeHeadlineCarousel
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Arrangement
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
import com.machina.jikan_client_compose.presentation.home_screen.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoilApi
@Composable
fun HomeContentList(
	navigator: HomeScreenNavigator,
	homeSections: StateFlow<HomeViewModel.HomeState>,
	modifier: Modifier = Modifier,
	getHomeSections: () -> Unit
) {
	val lazyListState = rememberLazyListState()
	val isScrolledToTheTop by lazyListState.isScrolledToTheFirst()
	val pullToRefreshState = rememberPullToRefreshState()

	val homeState by homeSections.collectAsState()
	val isLoading by produceState(initialValue = false, homeState) {
		value = homeState is HomeViewModel.HomeState.Loading
	}
	val isRefreshing by remember {
		derivedStateOf { pullToRefreshState.isRefreshing }
	}

	LaunchedEffect(isRefreshing) {
		if (isRefreshing) {
			getHomeSections()
		}
	}
	LaunchedEffect(isLoading) {
		if (!isLoading) {
			pullToRefreshState.endRefresh()
		}
	}

	val willRefresh = remember {
		derivedStateOf { pullToRefreshState.progress > 1f }
	}
	HomeContentRefreshHapticHandler(
		willRefresh = willRefresh,
		isRefreshing = isRefreshing,
		progress = pullToRefreshState.progress
	)


	val maxCardRotation = 5f
	val maxCardOffset = 200
	val cardOffset by animateIntAsState(
		targetValue = when {
			isRefreshing -> maxCardOffset
			pullToRefreshState.progress in 0f..1f -> (maxCardOffset * pullToRefreshState.progress).roundToInt()
			pullToRefreshState.progress > 1f -> (maxCardOffset + ((pullToRefreshState.progress - 1f) * .1f) * 100).roundToInt()
			else -> 0
		}, label = "cardOffset"
	)
	val cardRotation by animateFloatAsState(
		targetValue = when {
			isRefreshing || pullToRefreshState.progress > 1f -> maxCardRotation
			pullToRefreshState.progress > 0f -> 5 * pullToRefreshState.progress
			else -> 0f
		}, label = "cardRotation"
	)

	Box(modifier = modifier) {
		LazyColumn(
			state = lazyListState,
			modifier = Modifier
				.fillMaxSize()
				.let {
					if (isScrolledToTheTop) {
						it.nestedScroll(pullToRefreshState.nestedScrollConnection)
					} else {
						it
					}
				}
		) {
			when (val state = homeState) {
				is HomeViewModel.HomeState.Success,
				is HomeViewModel.HomeState.Loading -> {
					HomeSectionWithData(
						state = state,
						navigator = navigator,
						modifierProvider = { index ->
							Modifier.graphicsLayer {
								rotationZ = cardRotation * if (index % 2 == 0) 1 else -1
								translationY =
									(cardOffset * ((maxCardRotation - (index + 1)) / maxCardRotation)).dp
										.roundToPx()
										.toFloat()
							}
						}
					)

				}

				else -> {}
			}
		}

		GlowingBeamLoadingIndicator(
			pullToRefreshState = pullToRefreshState,
			isRefreshing = isRefreshing,
			maxHeight = cardOffset,
			modifier = Modifier.fillMaxWidth()
		)
	}
}

@Suppress("FunctionName")
private fun LazyListScope.HomeSectionWithData(
	state: HomeViewModel.HomeState,
	navigator: HomeScreenNavigator,
	modifierProvider: (Int) -> Modifier
) {
	if (state is HomeViewModel.HomeState.Success) {
		itemsIndexed(
			items = state.sections,
			key = { _, section -> section.id },
			contentType = { _, section -> section.type }
		) { index, section ->
			HomeSectionInternal(
				section = section,
				navigator = navigator,
				modifier = modifierProvider(index)
			)
		}
	} else if (state is HomeViewModel.HomeState.Loading) {
		itemsIndexed(
			items = state.sections,
			key = { _, section -> section.id },
			contentType = { _, section -> section.type }
		) { index, section ->
			HomeSectionInternal(
				section = section,
				navigator = navigator,
				modifier = modifierProvider(index)
			)
		}
	}
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeSectionInternal(
	section: HomeSection,
	navigator: HomeScreenNavigator,
	modifier: Modifier = Modifier
) {
	when (section.type) {
		is SectionType.AnimeAiringPopular -> {
			AnimeHeadlineCarousel(
				dataSet = section.contents,
				onClick = navigator::navigateToContentDetailsScreen,
				modifier = Modifier
					.fillMaxWidth()
					.then(modifier)
			)
		}

		is SectionType.AnimeSchedule -> {
			HorizontalContentHeader(
				title = Constant.AIRING_TODAY,
				onButtonClick = {
					navigator.navigateToContentViewAllScreen(
						title = Constant.AIRING_TODAY,
						url = Endpoints.getTodayScheduleAnimeEndpoints()
					)
				},
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 12.dp)
					.then(modifier)
			)

			ScrollableHorizontalContent(
				data = section,
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = Arrangement.Default,
				thumbnailHeight = Height.Small,
				onItemClick = navigator::navigateToContentDetailsScreen,
				modifier = modifier
			)
		}

		is SectionType.AnimeTop -> {
			HorizontalContentHeader(
				title = Constant.TOP_ANIME_OF_ALL_TIMES,
				onButtonClick = { },
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 12.dp)
					.then(modifier)
			)
			ScrollableHorizontalContent(
				data = section,
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = Arrangement.Default,
				thumbnailHeight = Height.Small,
				onItemClick = navigator::navigateToContentDetailsScreen,
				modifier = modifier
			)
		}
	}
}

@Composable
private fun HomeContentRefreshHapticHandler(
	willRefresh: State<Boolean>,
	isRefreshing: Boolean,
	progress: Float
) {
	val hapticFeedback = LocalHapticFeedback.current
	LaunchedEffect(willRefresh.value) {
		when {
			willRefresh.value -> {
				hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
				delay(70)
				hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
				delay(100)
				hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
			}

			!isRefreshing && progress > 0f -> {
				hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
			}
		}
	}
}
