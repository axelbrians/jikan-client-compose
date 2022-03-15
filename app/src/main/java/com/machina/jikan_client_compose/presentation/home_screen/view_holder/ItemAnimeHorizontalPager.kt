package com.machina.jikan_client_compose.presentation.home_screen.view_holder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.ui.theme.MyColor

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ItemAnimeHorizontalPager(
  modifier: Modifier = Modifier,
  data: AnimeAiringPopular,
  imagePainter: ImagePainter,
  onClick: () -> Unit = { }
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() }
  ) {
    if (imagePainter.state is ImagePainter.State.Loading) {
      CenterCircularProgressIndicator(
        strokeWidth = 2.dp,
        size = 20.dp,
        color = MyColor.Yellow500
      )
    } else {
      Image(
        painter = imagePainter,
        contentDescription = "Thumbnail",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxSize()
          .clip(RoundedCornerShape(12.dp))
      )
    }

    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(horizontal = 12.dp, vertical = 6.dp)
        .background(
          color = MyColor.BlackBlueBackground,
          shape = RoundedCornerShape(8.dp),
        )
        .zIndex(2f)
    ) {
      Text(
        text = data.title,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(horizontal = 12.dp, vertical = 6.dp),
        maxLines = 1,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
          color = MyColor.OnDarkSurface,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        ),
      )
    }
  }
}