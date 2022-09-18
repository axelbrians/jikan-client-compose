package com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ItemVerticalAnimeShimmer(
  modifier: Modifier,
  shimmerInstance: Shimmer,
  thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightDefault,
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

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.showItemVerticalAnimeShimmer(
  modifier: Modifier = ItemVerticalAnimeModifier.fillParentWidth,
  shimmerInstance: Shimmer,
  count: Int = 9,
  thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightDefault
) {
  items(count) {
    ItemVerticalAnimeShimmer(
      modifier = modifier,
      shimmerInstance = shimmerInstance,
      thumbnailHeight = thumbnailHeight
    )
  }
}

fun LazyListScope.showItemVerticalAnimeShimmer(
  modifier: Modifier = ItemVerticalAnimeModifier.Default,
  shimmerInstance: Shimmer,
  count: Int = 5,
  thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightDefault
) {
  items(count) {
    ItemVerticalAnimeShimmer(
      modifier = modifier,
      shimmerInstance = shimmerInstance,
      thumbnailHeight = thumbnailHeight
    )
  }
}