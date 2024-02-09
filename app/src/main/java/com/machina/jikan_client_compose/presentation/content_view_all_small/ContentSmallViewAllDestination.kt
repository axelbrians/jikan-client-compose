package com.machina.jikan_client_compose.presentation.content_view_all_small

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.navArgument
import com.machina.jikan_client_compose.core.extensions.scrollDirection
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableVerticalGridContent
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.VerticalGridModifier
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_normal.composable.ContentViewAllListScreenToolbar
import com.machina.jikan_client_compose.presentation.content_view_all_normal.data.ScrollDirection
import com.machina.jikan_client_compose.presentation.content_view_all_normal.viewmodel.ContentViewAllAnimeViewModel
import com.machina.jikan_client_compose.presentation.content_view_all_small.view_model.ContentSmallGridSizeViewModel
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
import com.machina.jikan_client_compose.ui.animation_spec.TweenSpec
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons

object ContentSmallViewAllDestination {
	const val KEY_NAV_ARGS = "contentViewAllListScreenNavArgs"

	val destination = destination {
		route = "home/content/view_all/small"
		requiredNav(
			navArgument(KEY_NAV_ARGS) {
				type = ContentViewAllListNavArgs
			}
		)
	}

	fun constructRoute(navArgs: ContentViewAllListNavArgs): String {
		return destination.createDestinationRoute(KEY_NAV_ARGS to navArgs)
	}
}

@Composable
fun ContentSmallViewAllScreen(
	navigator: ContentSmallViewAllNavigator,
	viewModel: ContentViewAllAnimeViewModel,
	gridSizeViewModel: ContentSmallGridSizeViewModel,
	navArgs: ContentViewAllListNavArgs
) {
	val shimmerInstance = rememberShimmerCustomBounds()
	val lazyGridState = rememberLazyGridState()
	val animateToolbarOffset = animateDpAsState(
		targetValue = if (lazyGridState.scrollDirection() == ScrollDirection.UP) 0.dp else (-56).dp,
		animationSpec = TweenSpec.defaultEasing(),
		label = "Animated Toolbar Offset"
	)

	val gridCount by gridSizeViewModel.gridSize
	val contentState by viewModel.contentState

	val appBarPainter = if (gridCount == 3) {
		MyIcons.Filled.grid3()
	} else {
		MyIcons.Filled.grid4()
	}

	val thumbnailHeight = if (gridCount == 3) {
		Height.Grid
	} else {
		Height.Small
	}

	val gridHorizontalPadding = if (gridCount == 3) {
		12.dp
	} else {
		8.dp
	}

	LaunchedEffect(key1 = viewModel) {
		viewModel.getNextContentPart(navArgs.url, navArgs.params)
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MyColor.DarkBlueBackground)
	) {
		ContentViewAllListScreenToolbar(
			modifier = Modifier
				.offset(y = animateToolbarOffset.value)
				.zIndex(2f),
			title = navArgs.title,
			onClick = navigator::navigateUp,
			trailingIcon = {
				IconButton(onClick = { gridSizeViewModel.setGridSize() }) {
					Icon(
						painter = appBarPainter,
						contentDescription = "Back",
						tint = MyColor.OnDarkSurfaceLight
					)
				}
			}
		)

		ScrollableVerticalGridContent(
			modifier = Modifier.fillMaxSize(),
			itemModifier = Modifier.fillMaxWidth(),
			shimmerInstance = shimmerInstance,
			contentState = contentState,
			thumbnailHeight = thumbnailHeight,
			gridCells = GridCells.Fixed(gridCount),
			lazyGridState = lazyGridState,
			verticalArrangement = VerticalGridModifier.VerticalArrangementMedium,
			horizontalArrangement = Arrangement.spacedBy(gridHorizontalPadding),
			textAlign = TextAlign.Center
		)
	}
}