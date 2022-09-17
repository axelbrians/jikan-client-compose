package com.machina.jikan_client_compose.presentation.home_screen.preview

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier

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
	override val count: Int
		get() = 3
	override val values: Sequence<ItemVerticalAnimeState>
		get() = listOf(
			ItemVerticalAnimeState(
				modifier = ItemVerticalAnimeModifier.Default,
				thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightDefault
			),
			ItemVerticalAnimeState(
				modifier = ItemVerticalAnimeModifier.Small,
				thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightGrid,
			),
			ItemVerticalAnimeState(
				modifier = ItemVerticalAnimeModifier.Small,
				thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightSmall,
			)
		).asSequence()
}