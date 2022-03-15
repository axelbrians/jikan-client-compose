package com.machina.jikan_client_compose.presentation.home_screen.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ItemAnimeTopShimmer(
  shimmerInstance: Shimmer
) {
  Column(
    modifier = Modifier
      .shimmer(shimmerInstance)
      .width(160.dp)
      .padding(12.dp, 0.dp)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(190.dp)
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