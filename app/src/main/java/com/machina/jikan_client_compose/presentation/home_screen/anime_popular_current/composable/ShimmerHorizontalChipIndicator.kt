package com.machina.jikan_client_compose.presentation.home_screen.anime_popular_current.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerHorizontalChipIndicator(shimmerInstance: Shimmer) {
  Row(horizontalArrangement = Arrangement.Center) {
    Box(
      modifier = Modifier
        .width(120.dp)
        .height(8.dp)
        .clip(RoundedCornerShape(50f))
        .shimmer(shimmerInstance)
        .background(MyColor.Grey),
    ) { }
  }
}