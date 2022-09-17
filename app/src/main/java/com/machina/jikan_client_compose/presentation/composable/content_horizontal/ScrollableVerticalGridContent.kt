package com.machina.jikan_client_compose.presentation.composable.content_horizontal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.presentation.data.StateWrapper
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.showItemVerticalAnimeShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.valentinilk.shimmer.Shimmer

object VerticalGridModifier {
	val VerticalArrangementDefault = Arrangement.spacedBy(4.dp)
	val HorizontalArrangementDefault = Arrangement.spacedBy(6.dp)

	val VerticalArrangementMedium = Arrangement.spacedBy(6.dp)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun ScrollableVerticalGridContent(
	modifier: Modifier = Modifier,
	itemModifier: Modifier = ItemVerticalAnimeModifier.fillParentWidth,
	shimmerInstance: Shimmer = rememberShimmerCustomBounds(),
	contentState: StateWrapper<AnimeVerticalModel>,
	contentPadding: PaddingValues = PaddingValues(start = 12.dp, end = 12.dp, top = 64.dp, bottom = 12.dp),
	thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightGrid,
	gridCells: GridCells = GridCells.Fixed(3),
	lazyGridState: LazyListState = rememberLazyListState(),
	verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
	horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(0.dp),
	textAlign: TextAlign = TextAlign.Start
) {

	LazyVerticalGrid(
		modifier = modifier
			.onUpdateShimmerBounds(shimmerInstance),
		cells = gridCells,
		state = lazyGridState,
		contentPadding = contentPadding,
		verticalArrangement = verticalArrangement,
		horizontalArrangement = horizontalArrangement
	) {
		if (contentState.data != null) {
			items(contentState.data.data) { character: AnimeVerticalDataModel ->
				ItemVerticalAnime(
					modifier = itemModifier,
					data = character,
					thumbnailHeight = thumbnailHeight,
					textAlign = textAlign,
					onClick = { _, _ -> }
				)
			}
		}

		if (contentState.isLoading) {
			showItemVerticalAnimeShimmer(
				modifier = itemModifier,
				shimmerInstance = shimmerInstance,
				thumbnailHeight = thumbnailHeight
			)
		}
	}
}