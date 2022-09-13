package com.machina.jikan_client_compose.presentation.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.showItemVerticalAnimeShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.presentation.home_screen.item.showItemVerticalAnimeMoreWhenPastLimit
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.valentinilk.shimmer.Shimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ScrollableHorizontalContent(
	modifier: Modifier,
	headerModifier: Modifier = HorizontalContentHeaderConfig.default,
	itemModifier: Modifier = ItemVerticalAnimeModifier.default,
	shimmer: Shimmer = rememberShimmerCustomBounds(),
	headerTitle: String,
	contentState: StateListWrapper<AnimeVerticalDataModel>,
	contentPadding: PaddingValues,
	contentArrangement: Arrangement.Horizontal,
	onIconClick: () -> Unit,
	onItemClick: (Int, ContentType) -> Unit,
) {

	if (contentState.isLoading) {
		ContentListHeaderWithButtonShimmer(shimmerInstance = shimmer)
	} else {
		HorizontalContentHeader(
			modifier = headerModifier,
			title = headerTitle,
			onButtonClick = onIconClick
		)
	}

	LazyRow(
		contentPadding = contentPadding,
		modifier = modifier.onUpdateShimmerBounds(shimmer),
		horizontalArrangement = contentArrangement
	) {
		if (contentState.isLoading) {
			showItemVerticalAnimeShimmer(shimmer)
		} else {
			items(contentState.data, key = { it.malId }) { data ->
				ItemVerticalAnime(
					modifier = itemModifier,
					data = data,
					onClick = onItemClick
				)
			}
			showItemVerticalAnimeMoreWhenPastLimit(
				modifier = itemModifier,
				thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightDefault,
				size = contentState.data.size,
				onClick = onIconClick
			)
		}
	}
}