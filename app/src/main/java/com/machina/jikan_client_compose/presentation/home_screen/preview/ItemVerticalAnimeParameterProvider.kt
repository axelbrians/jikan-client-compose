package com.machina.jikan_client_compose.presentation.home_screen.preview

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height

data class ItemVerticalAnimeState(
	val modifier: Modifier = Modifier,
	val thumbnailHeight: Dp,
	val data: AnimeVerticalDataModel = AnimeVerticalDataModel(
		malId = 5114,
		title = "Fullmetal Alchemist: Brotherhood",
		score=9.13,
		imageUrl="https://cdn.myanimelist.net/images/anime/1223/96541.jpg"
	),
	val onClick: (Int, ContentType) -> Unit = { _, _ -> }
)

class ItemVerticalAnimeProvider: PreviewParameterProvider<ItemVerticalAnimeState> {
	override val values: Sequence<ItemVerticalAnimeState>
		get() = listOf(
			ItemVerticalAnimeState(
				modifier = Modifier.width(CardThumbnailPortraitDefault.Width.Default),
				thumbnailHeight = Height.Default
			),
			ItemVerticalAnimeState(
				modifier = Modifier.width(CardThumbnailPortraitDefault.Width.Small),
				thumbnailHeight = Height.Grid,
			),
			ItemVerticalAnimeState(
				modifier = Modifier.width(CardThumbnailPortraitDefault.Width.Small),
				thumbnailHeight = Height.Small,
			)
		).asSequence()
}