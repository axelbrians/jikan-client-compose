package com.machina.jikan_client_compose.presentation.content_small_view_all

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.machina.jikan_client_compose.core.extensions.scrollDirection
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableVerticalGridContent
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.VerticalGridModifier
import com.machina.jikan_client_compose.presentation.content_small_view_all.nav.ContentSmallViewAllNavigator
import com.machina.jikan_client_compose.presentation.content_small_view_all.view_model.ContentSmallGridSizeViewModel
import com.machina.jikan_client_compose.presentation.content_view_all_screen.composable.ContentViewAllListScreenToolbar
import com.machina.jikan_client_compose.presentation.content_view_all_screen.data.ScrollDirection
import com.machina.jikan_client_compose.presentation.content_view_all_screen.nav.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllAnimeViewModel
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.ui.animation_spec.TweenSpec
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons

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
		animationSpec = TweenSpec.defaultEasing()
	)

	val gridCount by gridSizeViewModel.gridSize
	val contentState by viewModel.contentState

	val appBarPainter = if (gridCount == 3) {
		MyIcons.Filled.grid3()
	} else {
		MyIcons.Filled.grid4()
	}

	val thumbnailHeight = if (gridCount == 3) {
		ItemVerticalAnimeModifier.ThumbnailHeightGrid
	} else {
		ItemVerticalAnimeModifier.ThumbnailHeightSmall
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
			itemModifier = ItemVerticalAnimeModifier.fillParentWidth,
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