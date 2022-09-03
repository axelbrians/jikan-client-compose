package com.machina.jikan_client_compose.presentation.content_small_view_all

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.extensions.scrollDirection
import com.machina.jikan_client_compose.domain.model.anime.AnimeCharacterModel
import com.machina.jikan_client_compose.presentation.content_detail_screen.item.ItemAnimeCharacter
import com.machina.jikan_client_compose.presentation.content_small_view_all.nav.ContentSmallViewAllNavigator
import com.machina.jikan_client_compose.presentation.content_view_all_screen.composable.ContentViewAllListScreenToolbar
import com.machina.jikan_client_compose.presentation.content_view_all_screen.data.ScrollDirection
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.ui.animation_spec.TweenSpec
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun ContentSmallViewAllScreen(
	navigator: ContentSmallViewAllNavigator
) {
	var gridCount by remember { mutableStateOf(2) }
	val shimmerInstance = rememberShimmerCustomBounds()
	val lazyGridState = rememberLazyListState()
	val animateToolbarOffset = animateDpAsState(
		targetValue = if (lazyGridState.scrollDirection() == ScrollDirection.UP) 0.dp else (-56).dp,
		animationSpec = TweenSpec.defaultEasing()
	)

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MyColor.DarkBlueBackground)
	) {
		ContentViewAllListScreenToolbar(
			modifier = Modifier
				.offset(y = animateToolbarOffset.value)
				.zIndex(2f),
			title = "Characters all",
			onClick = navigator::navigateUp
		)
		Button(
			modifier = Modifier.align(Alignment.TopEnd).zIndex(3f),
			onClick = {
				gridCount = if (gridCount == 4) 2 else 4
			}
		) {
			Text(text = "Change Grid")
		}
		LazyVerticalGrid(
			modifier = Modifier
				.fillMaxSize()
				.onUpdateShimmerBounds(shimmerInstance),
			cells = GridCells.Fixed(gridCount),
			state = lazyGridState,
			contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 64.dp, bottom = 12.dp),
			verticalArrangement = Arrangement.spacedBy(0.dp)
		) {
			items(100) {
				ItemAnimeCharacter(
					modifier = ItemVerticalAnimeModifier.fillParentWidth,
					data = AnimeCharacterModel(
						malId = 0,
						name = "Chisato",
						imageUrl = "https://cdn.myanimelist.net/images/characters/5/486674.jpg?s=0608ef72094b43274983a365006865b0",
						role = "super",
						url = "https://cdn.myanimelist.net/images/characters/5/486674.jpg?s=0608ef72094b43274983a365006865b0"
					)
				)
			}
		}
	}
}