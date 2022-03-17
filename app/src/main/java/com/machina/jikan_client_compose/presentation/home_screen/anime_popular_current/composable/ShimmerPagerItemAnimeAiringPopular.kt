package com.machina.jikan_client_compose.presentation.home_screen.anime_popular_current.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScope.ShimmerPagerItemAnimeAiringPopular(
  shimmerInstance: Shimmer,
  currentPage: Int,
) {
  Surface(
    color = Color.Transparent,
    modifier = Modifier
      .graphicsLayer {
        // Calculate the absolute offset for the current page from the
        // scroll position. We use the absolute value which allows us to mirror
        // any effects for both directions
        val pageOffset = calculateCurrentOffsetForPage(currentPage).absoluteValue
        // We animate the scaleX + scaleY, between 85% and 100%
        lerp(
          start = 0.85f,
          stop = 1f,
          fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
          scaleX = scale
          scaleY = scale
        }

        // We animate the alpha, between 50% and 100%
        alpha = lerp(
          start = 0.5f,
          stop = 1f,
          fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
      }
      .fillMaxSize()
      .aspectRatio(1.3f)
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(12.dp))
        .shimmer(shimmerInstance)
        .background(color = MyColor.Grey)
    ) { }
  }
}