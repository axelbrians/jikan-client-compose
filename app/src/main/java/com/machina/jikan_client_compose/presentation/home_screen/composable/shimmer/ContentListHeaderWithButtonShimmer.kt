package com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ContentListHeaderWithButtonShimmer(
  shimmerInstance: Shimmer
) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(12.dp).shimmer(shimmerInstance),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .width(110.dp)
        .height(32.dp)
        .padding(0.dp, 6.dp, 0.dp, 0.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(color = MyColor.Grey)
    )

    Box(
      modifier = Modifier
        .size(32.dp)
        .padding(0.dp, 6.dp, 0.dp, 0.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(color = MyColor.Grey)
    )
  }
}