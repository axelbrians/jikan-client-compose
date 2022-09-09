package com.machina.jikan_client_compose.presentation.content_detail_screen.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ItemAnimeCharacterShimmer(
	modifier: Modifier = Modifier,
	thumbnailHeight: Dp,
	shimmerInstance: Shimmer
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
	}
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.showItemContentSmallShimmer(
	modifier: Modifier = Modifier,
	thumbnailHeight: Dp = ItemAnimeCharacterConfig.ThumbnailHeightFour,
	shimmerInstance: Shimmer
) {
	items(9) {
		ItemAnimeCharacterShimmer(
			modifier = modifier,
			thumbnailHeight = thumbnailHeight,
			shimmerInstance = shimmerInstance
		)
	}
}

fun LazyListScope.showItemContentSmallShimmer(
	modifier: Modifier = ItemAnimeCharacterConfig.default,
	thumbnailHeight: Dp = ItemAnimeCharacterConfig.ThumbnailHeightFour,
	shimmerInstance: Shimmer
) {
	items(9) {
		ItemAnimeCharacterShimmer(
			modifier = modifier,
			thumbnailHeight = thumbnailHeight,
			shimmerInstance = shimmerInstance
		)
	}
}