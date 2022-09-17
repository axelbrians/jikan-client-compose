package com.machina.jikan_client_compose.presentation.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier

data class ScrollableHorizontalContentState(
	val modifier: Modifier,
	val headerModifier: Modifier = HorizontalContentHeaderConfig.Default,
	val itemModifier: Modifier = ItemVerticalAnimeModifier.Default,
	val thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightDefault,
	val headerTitle: String = "Title",
	val contentState: StateListWrapper<AnimeVerticalDataModel>,
	val contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
	val contentArrangement: Arrangement.Horizontal,
	val textAlign: TextAlign = TextAlign.Start,
	val onIconClick: () -> Unit = { },
	val onItemClick: (Int, ContentType) -> Unit = { _, _ -> },
)

private val DataSet = List(10) {
	AnimeVerticalDataModel(
		malId = 50709+it,
		title = "Lycoris Recoil",
		score = 8.27,
		imageUrl = "https://cdn.myanimelist.net/images/anime/1392/124401.jpg"
	)
}

class ScrollableHorizontalContentParameterProvider
	: PreviewParameterProvider<ScrollableHorizontalContentState> {
	override val count: Int
		get() = super.count
	override val values: Sequence<ScrollableHorizontalContentState>
		get() = listOf(
			ScrollableHorizontalContentState(
				modifier = Modifier,
				headerTitle = "Scrollable Default",
				contentState = StateListWrapper.loading(),
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default
			),
			ScrollableHorizontalContentState(
				modifier = Modifier,
				headerTitle = "Scrollable Default",
				contentState = StateListWrapper(data = DataSet),
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default,
			),
			ScrollableHorizontalContentState(
				modifier = Modifier,
				itemModifier = ItemVerticalAnimeModifier.Small,
				thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightSmall,
				headerTitle = "Scrollable Small",
				contentState = StateListWrapper.loading(),
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default
			),
			ScrollableHorizontalContentState(
				modifier = Modifier,
				itemModifier = ItemVerticalAnimeModifier.Small,
				thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightSmall,
				headerTitle = "Scrollable Small",
				contentState = StateListWrapper(data = DataSet),
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default,
				textAlign = TextAlign.Center
			)
		).asSequence()
}

