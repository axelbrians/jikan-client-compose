package com.machina.jikan_client_compose.presentation.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.showCardThumbnailPortraitShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortrait
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Width.Small
import com.machina.jikan_client_compose.presentation.home_screen.item.showItemVerticalAnimeMoreWhenPastLimit
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ScrollableHorizontalContent(
	headerTitle: String,
	data: HomeSection,
	contentPadding: PaddingValues,
	contentArrangement: Arrangement.Horizontal,
	modifier: Modifier = Modifier,
	headerModifier: Modifier = HorizontalContentHeaderConfig.Default,
	itemModifier: Modifier = Modifier.width(Small),
	thumbnailHeight: Dp = CardThumbnailPortraitDefault.Height.Default,
	textAlign: TextAlign = TextAlign.Start,
	onIconClick: () -> Unit,
	onItemClick: (Int, ContentType) -> Unit,
) {
	HorizontalContentHeader(
		modifier = headerModifier,
		title = headerTitle,
		onButtonClick = onIconClick
	)

	LazyRow(
		modifier = modifier,
		contentPadding = contentPadding,
		horizontalArrangement = contentArrangement
	) {
		items(
			items = data.contents.take(Constant.HORIZONTAL_CONTENT_LIMIT),
			key = { it.malId }
		) { data ->
			CardThumbnailPortrait(
				imageUrl = data.imageUrl,
				text = data.title,
				modifier = itemModifier,
				thumbnailHeight = thumbnailHeight,
				textAlign = textAlign,
				onClick = remember { { onItemClick(data.malId, ContentType.Anime) } }
			)
		}
		showItemVerticalAnimeMoreWhenPastLimit(
			modifier = itemModifier,
			thumbnailHeight = thumbnailHeight,
			size = data.contents.size,
			onClick = onIconClick
		)
	}
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ScrollableHorizontalContent(
	headerTitle: String,
	contentState: StateListWrapper<AnimePortraitDataModel>,
	contentPadding: PaddingValues,
	contentArrangement: Arrangement.Horizontal,
	modifier: Modifier = Modifier,
	headerModifier: Modifier = HorizontalContentHeaderConfig.Default,
	itemModifier: Modifier = Modifier.width(Small),
	thumbnailHeight: Dp = CardThumbnailPortraitDefault.Height.Default,
	shimmer: Shimmer = rememberShimmerCustomBounds(),
	textAlign: TextAlign = TextAlign.Start,
	onHeaderClick: () -> Unit,
	onItemClick: (Int, ContentType) -> Unit,
) {
	if (contentState.isLoading) {
		ContentListHeaderWithButtonShimmer(shimmerInstance = shimmer)
	} else if (contentState.data.isNotEmpty()) {
		HorizontalContentHeader(
			modifier = headerModifier,
			title = headerTitle,
			onButtonClick = onHeaderClick
		)
	}

	LazyRow(
		modifier = modifier.onUpdateShimmerBounds(shimmer),
		contentPadding = contentPadding,
		horizontalArrangement = contentArrangement
	) {
		if (contentState.isLoading) {
			showCardThumbnailPortraitShimmer(
				modifier = itemModifier,
				shimmerInstance = shimmer,
				thumbnailHeight = thumbnailHeight
			)
		} else if (contentState.data.isNotEmpty()) {
			items(
				contentState.data.take(Constant.HORIZONTAL_CONTENT_LIMIT),
				key = { it.malId }
			) { data ->
				CardThumbnailPortrait(
					modifier = itemModifier,
					data = data,
					thumbnailHeight = thumbnailHeight,
					textAlign = textAlign,
					onClick = onItemClick
				)
			}
			showItemVerticalAnimeMoreWhenPastLimit(
				modifier = itemModifier,
				thumbnailHeight = thumbnailHeight,
				size = contentState.data.size,
				onClick = onHeaderClick
			)
		}
	}
}

@Preview
@Composable
private fun Preview_ScrollableHorizontalContent(
	@PreviewParameter(ScrollableHorizontalContentParameterProvider::class) state: ScrollableHorizontalContentState
) {
	val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
	Column {
		ScrollableHorizontalContent(
			modifier = state.modifier,
			headerModifier = state.headerModifier,
			itemModifier = state.itemModifier,
			shimmer = shimmerInstance,
			thumbnailHeight = state.thumbnailHeight,
			headerTitle = state.headerTitle,
			contentState = state.contentState,
			contentPadding = state.contentPadding,
			contentArrangement = state.contentArrangement,
			onHeaderClick = state.onIconClick,
			onItemClick = state.onItemClick
		)
	}
}