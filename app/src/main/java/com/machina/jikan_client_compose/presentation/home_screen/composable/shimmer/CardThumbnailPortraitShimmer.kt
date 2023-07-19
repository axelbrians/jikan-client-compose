package com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Width
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CardThumbnailPortraitShimmer(
	modifier: Modifier,
	shimmerInstance: Shimmer,
	thumbnailHeight: Dp = Height.Default,
) {
	Column(
		modifier = modifier
			.shimmer(shimmerInstance)
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.height(thumbnailHeight)
				.clip(RoundedCornerShape(12.dp))
				.background(color = MyColor.Grey)
		)

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.height(18.dp)
				.padding(0.dp, 6.dp, 0.dp, 0.dp)
				.background(color = MyColor.Grey)
		)

		Box(
			modifier = Modifier
				.width(62.dp)
				.height(18.dp)
				.padding(0.dp, 6.dp, 0.dp, 0.dp)
				.background(color = MyColor.Grey)
		)

		Box(
			modifier = Modifier
				.width(32.dp)
				.height(18.dp)
				.padding(0.dp, 6.dp, 0.dp, 0.dp)
				.background(color = MyColor.Grey)
		)

	}
}

fun LazyGridScope.showCardThumbnailPortraitShimmer(
	shimmerInstance: Shimmer,
	modifier: Modifier = Modifier.fillMaxWidth(),
	count: Int = 9,
	thumbnailHeight: Dp = Height.Default
) {
	items(count) {
		CardThumbnailPortraitShimmer(
			modifier = modifier,
			shimmerInstance = shimmerInstance,
			thumbnailHeight = thumbnailHeight
		)
	}
}

fun LazyListScope.showCardThumbnailPortraitShimmer(
	shimmerInstance: Shimmer,
	modifier: Modifier = Modifier.width(Width.Default),
	count: Int = 5,
	thumbnailHeight: Dp = Height.Default
) {
	items(count) {
		CardThumbnailPortraitShimmer(
			modifier = modifier,
			shimmerInstance = shimmerInstance,
			thumbnailHeight = thumbnailHeight
		)
	}
}