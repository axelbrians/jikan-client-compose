package com.machina.jikan_client_compose.presentation.composable.content_horizontal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.presentation.data.StateWrapper
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.showCardThumbnailPortraitShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortrait
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.valentinilk.shimmer.Shimmer

object VerticalGridModifier {
	val VerticalArrangementDefault = Arrangement.spacedBy(4.dp)
	val HorizontalArrangementDefault = Arrangement.spacedBy(6.dp)

	val VerticalArrangementMedium = Arrangement.spacedBy(6.dp)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ScrollableVerticalGridContent(
	contentState: StateWrapper<AnimeVerticalModel>,
	modifier: Modifier = Modifier,
	itemModifier: Modifier = Modifier.fillMaxWidth(),
	shimmerInstance: Shimmer = rememberShimmerCustomBounds(),
	contentPadding: PaddingValues = PaddingValues(start = 12.dp, end = 12.dp, top = 64.dp, bottom = 12.dp),
	thumbnailHeight: Dp = CardThumbnailPortraitDefault.Height.Grid,
	gridCells: GridCells = GridCells.Fixed(3),
	lazyGridState: LazyGridState = rememberLazyGridState(),
	verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
	horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(0.dp),
	textAlign: TextAlign = TextAlign.Start
) {

	LazyVerticalGrid(
		modifier = modifier
			.onUpdateShimmerBounds(shimmerInstance),
		columns = gridCells,
		state = lazyGridState,
		contentPadding = contentPadding,
		verticalArrangement = verticalArrangement,
		horizontalArrangement = horizontalArrangement
	) {
		if (contentState.data != null) {
			items(contentState.data.data) { character: AnimePortraitDataModel ->
				CardThumbnailPortrait(
					modifier = itemModifier,
					data = character,
					thumbnailHeight = thumbnailHeight,
					textAlign = textAlign,
					onClick = { _, _ -> }
				)
			}
		}

		if (contentState.isLoading) {
			showCardThumbnailPortraitShimmer(
				modifier = itemModifier,
				shimmerInstance = shimmerInstance,
				thumbnailHeight = thumbnailHeight
			)
		}
	}
}