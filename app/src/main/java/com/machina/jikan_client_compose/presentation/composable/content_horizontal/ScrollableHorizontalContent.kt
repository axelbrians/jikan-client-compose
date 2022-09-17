package com.machina.jikan_client_compose.presentation.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
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
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ScrollableHorizontalContent(
	modifier: Modifier,
	headerModifier: Modifier = HorizontalContentHeaderConfig.Default,
	itemModifier: Modifier = ItemVerticalAnimeModifier.Default,
	shimmer: Shimmer = rememberShimmerCustomBounds(),
	thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightDefault,
	headerTitle: String,
	contentState: StateListWrapper<AnimeVerticalDataModel>,
	contentPadding: PaddingValues,
	contentArrangement: Arrangement.Horizontal,
	onIconClick: () -> Unit,
	onItemClick: (Int, ContentType) -> Unit,
) {
	if (contentState.isLoading) {
		ContentListHeaderWithButtonShimmer(shimmerInstance = shimmer)
	} else if (contentState.data.isNotEmpty()) {
		HorizontalContentHeader(
			modifier = headerModifier,
			title = headerTitle,
			onButtonClick = onIconClick
		)
	}

	LazyRow(
		modifier = modifier.onUpdateShimmerBounds(shimmer),
		contentPadding = contentPadding,
		horizontalArrangement = contentArrangement
	) {
		if (contentState.isLoading) {
			showItemVerticalAnimeShimmer(
				modifier = itemModifier,
				shimmerInstance = shimmer,
				thumbnailHeight = thumbnailHeight
			)
		} else if (contentState.data.isNotEmpty()) {
			items(contentState.data, key = { it.malId }) { data ->
				ItemVerticalAnime(
					modifier = itemModifier,
					data = data,
					thumbnailHeight = thumbnailHeight,
					onClick = onItemClick
				)
			}
			showItemVerticalAnimeMoreWhenPastLimit(
				modifier = itemModifier,
				thumbnailHeight = thumbnailHeight,
				size = contentState.data.size,
				onClick = onIconClick
			)
		}
	}
}

@Preview
@Composable
fun Preview_ScrollableHorizontalContent(
	@PreviewParameter(ScrollableHorizontalContentParameterProvider::class) state: ScrollableHorizontalContentState
) {
	val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
	Column {
		ScrollableHorizontalContent(
			modifier = state.modifier.onUpdateShimmerBounds(shimmerInstance),
			headerModifier = state.headerModifier,
			itemModifier = state.itemModifier,
			shimmer = shimmerInstance,
			thumbnailHeight = state.thumbnailHeight,
			headerTitle = state.headerTitle,
			contentState = state.contentState,
			contentPadding = state.contentPadding,
			contentArrangement = state.contentArrangement,
			onIconClick = state.onIconClick,
			onItemClick = state.onItemClick
		)
	}
}