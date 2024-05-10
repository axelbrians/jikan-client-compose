package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import com.machina.jikan_client_compose.core.extensions.isScrolledToTheFirst
import com.machina.jikan_client_compose.presentation.composable.pull_to_refresh.GlowingBeamLoadingIndicator
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemHomeSection
import com.machina.jikan_client_compose.presentation.home_screen.viewmodel.HomeViewModel.HomeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt


private const val maxCardRotation = 5f
private const val maxCardOffset = 200

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoilApi
@Composable
fun HomeContentList(
	navigator: HomeScreenNavigator,
	homeSections: StateFlow<HomeState>,
	modifier: Modifier = Modifier,
	lazyListState: LazyListState = rememberLazyListState(),
	getHomeSections: () -> Unit
) {
	val isScrolledToTheTop by lazyListState.isScrolledToTheFirst()

	val homeState by homeSections.collectAsState()
	val isLoading by produceState(initialValue = false, homeState) {
		value = homeState is HomeState.Loading
	}

	val pullToRefreshState = rememberPullToRefreshState()
	val isRefreshing by remember {
		derivedStateOf { pullToRefreshState.isRefreshing }
	}
	val willRefresh = remember {
		derivedStateOf { pullToRefreshState.progress > 1f }
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
	HomeContentRefreshHapticHandler(
		willRefresh = willRefresh,
		isRefreshing = isRefreshing,
		progress = pullToRefreshState.progress
	)

	val (cardOffset, cardRotation) = getOffsetAndRotation(
		isRefreshing = isRefreshing,
		progress = pullToRefreshState.progress
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
				is HomeState.Success,
				is HomeState.Loading -> {
					ItemHomeSection(
						state = state,
						navigator = navigator,
						modifierProvider = { index ->
							Modifier.graphicsLayer {
								val rotateDirection = if (index % 2 == 0) 1 else -1
								rotationZ = cardRotation.value * rotateDirection
								translationY =
									(cardOffset.value * ((maxCardRotation - (index + 1)) / maxCardRotation)).dp
										.roundToPx()
										.toFloat()
							}
						}
					)

				}

				is HomeState.Init -> Unit
			}
		}

		GlowingBeamLoadingIndicator(
			pullToRefreshState = pullToRefreshState,
			isRefreshing = isRefreshing,
			maxHeight = cardOffset.value,
			modifier = Modifier.fillMaxWidth()
		)
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

@Composable
private fun getOffsetAndRotation(
	isRefreshing: Boolean,
	progress: Float
): Pair<State<Int>, State<Float>> {
	val cardOffset = animateIntAsState(
		targetValue = when {
			isRefreshing -> maxCardOffset
			progress in 0f..1f -> (maxCardOffset * progress).roundToInt()
			progress > 1f -> (maxCardOffset + ((progress - 1f) * .1f) * 100).roundToInt()
			else -> 0
		}, label = "cardOffset"
	)
	val cardRotation = animateFloatAsState(
		targetValue = when {
			isRefreshing || progress > 1f -> maxCardRotation
			progress > 0f -> 5 * progress
			else -> 0f
		}, label = "cardRotation"
	)

	return cardOffset to cardRotation
}