package com.machina.jikan_client_compose.presentation.home_screen.preview

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier


data class ItemVerticalAnimeMoreState(
	val modifier: Modifier = Modifier,
	val thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightDefault
)

class ItemVerticalAnimeMoreParameterProvider
	: PreviewParameterProvider<ItemVerticalAnimeMoreState> {
	override val count: Int
		get() = 2
	override val values: Sequence<ItemVerticalAnimeMoreState>
		get() = listOf(
			ItemVerticalAnimeMoreState(
				modifier = ItemVerticalAnimeModifier.Default,
				thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightDefault
			),
			ItemVerticalAnimeMoreState(
				modifier = ItemVerticalAnimeModifier.Small,
				thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightSmall
			)
		).asSequence()
}