package com.machina.jikan_client_compose.presentation.home_screen.preview

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Width


data class ItemVerticalAnimeMoreState(
	val modifier: Modifier = Modifier,
	val thumbnailHeight: Dp = Height.Default
)

class ItemVerticalAnimeMoreParameterProvider
	: PreviewParameterProvider<ItemVerticalAnimeMoreState> {
	override val values: Sequence<ItemVerticalAnimeMoreState>
		get() = listOf(
			ItemVerticalAnimeMoreState(
				modifier = Modifier.width(Width.Default),
				thumbnailHeight = Height.Default
			),
			ItemVerticalAnimeMoreState(
				modifier = Modifier.width(Width.Small),
				thumbnailHeight = Height.Small
			)
		).asSequence()
}