package com.machina.jikan_client_compose.presentation.content_small_view_all

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.extensions.scrollDirection
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.content_detail_screen.item.ItemAnimeCharacter
import com.machina.jikan_client_compose.presentation.content_detail_screen.item.ItemAnimeCharacterConfig
import com.machina.jikan_client_compose.presentation.content_small_view_all.nav.ContentSmallViewAllNavigator
import com.machina.jikan_client_compose.presentation.content_view_all_screen.composable.ContentViewAllListScreenToolbar
import com.machina.jikan_client_compose.presentation.content_view_all_screen.data.ScrollDirection
import com.machina.jikan_client_compose.presentation.content_view_all_screen.nav.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllAnimeViewModel
import com.machina.jikan_client_compose.ui.animation_spec.TweenSpec
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun ContentSmallViewAllScreen(
	navigator: ContentSmallViewAllNavigator,
	viewModel: ContentViewAllAnimeViewModel,
	navArgs: ContentViewAllListNavArgs
) {
	var gridCount by remember { mutableStateOf(4) }
	val shimmerInstance = rememberShimmerCustomBounds()
	val lazyGridState = rememberLazyListState()
	val animateToolbarOffset = animateDpAsState(
		targetValue = if (lazyGridState.scrollDirection() == ScrollDirection.UP) 0.dp else (-56).dp,
		animationSpec = TweenSpec.defaultEasing()
	)

	val contentState = viewModel.contentState.value

	// TODO: Save grid setting to Shared Preference
	val appBarPainter = if (gridCount == 3) {
		MyIcons.Filled.grid3()
	} else {
		MyIcons.Filled.grid4()
	}

	val itemModifier = if (gridCount == 3) {
		ItemAnimeCharacterConfig.threeColumn
	} else {
		ItemAnimeCharacterConfig.fourColumn
	}

	val gridHorizontalPadding = if (gridCount == 3) {
		12.dp
	} else {
		8.dp
	}

	LaunchedEffect(key1 = viewModel) {
		viewModel.getNextContentPart(navArgs.url, navArgs.params)
	}

	Icons.Default.ArrowBack
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
				IconButton(onClick = { gridCount = if (gridCount == 3) 4 else 3 }) {
					Icon(
						painter = appBarPainter,
						contentDescription = "Back",
						tint = MyColor.OnDarkSurfaceLight
					)
				}
			}
		)

		LazyVerticalGrid(
			modifier = Modifier
				.fillMaxSize()
				.onUpdateShimmerBounds(shimmerInstance),
			cells = GridCells.Fixed(gridCount),
			state = lazyGridState,
			contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 64.dp, bottom = 12.dp),
			verticalArrangement = Arrangement.spacedBy(0.dp),
			horizontalArrangement = Arrangement.spacedBy(gridHorizontalPadding)
		) {
			items(contentState.data.data) { character: AnimeVerticalDataModel ->
				ItemAnimeCharacter(
					modifier = itemModifier,
					title = character.title,
					imageUrl = character.imageUrl
				)
			}
		}
	}
}