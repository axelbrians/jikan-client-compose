package com.machina.jikan_client_compose.presentation.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Arrangement
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Width

data class ScrollableHorizontalContentState(
	val headerTitle: String = "Title",
	val contentState: StateListWrapper<AnimePortraitDataModel>,
	val contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
	val contentArrangement: androidx.compose.foundation.layout.Arrangement.Horizontal.Horizontal,
	val modifier: Modifier,
	val headerModifier: Modifier = HorizontalContentHeaderConfig.Default,
	val itemModifier: Modifier = Modifier.width(Width.Default),
	val thumbnailHeight: Dp = Height.Default,
	val textAlign: TextAlign = TextAlign.Start,
	val onIconClick: () -> Unit = { },
	val onItemClick: (Int, ContentType) -> Unit = { _, _ -> },
)

private val DataSet = List(10) {
	AnimePortraitDataModel(
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
				contentArrangement = Arrangement.Default
			),
			ScrollableHorizontalContentState(
				modifier = Modifier,
				headerTitle = "Scrollable Default",
				contentState = StateListWrapper(data = DataSet),
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = Arrangement.Default,
			),
			ScrollableHorizontalContentState(
				modifier = Modifier,
				itemModifier = Modifier.width(Width.Small),
				thumbnailHeight = Height.Small,
				headerTitle = "Scrollable Small",
				contentState = StateListWrapper.loading(),
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = Arrangement.Default
			),
			ScrollableHorizontalContentState(
				modifier = Modifier,
				itemModifier = Modifier.width(Width.Small),
				thumbnailHeight = Height.Small,
				headerTitle = "Scrollable Small",
				contentState = StateListWrapper(data = DataSet),
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = Arrangement.Default,
				textAlign = TextAlign.Center
			)
		).asSequence()
}

