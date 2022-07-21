package com.machina.jikan_client_compose.presentation.home_screen.view_holder

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.ui.theme.MyColor

object ItemVerticalAnimeConfig {
  val defaultModifier = Modifier
    .width(140.dp)
    .padding(6.dp, 4.dp)

  val fillParentWidthModifier = Modifier
    .fillMaxWidth()
    .padding(6.dp, 4.dp)

}

@ExperimentalCoilApi
@Composable
fun ItemVerticalAnime(
  modifier: Modifier = Modifier,
  anime: AnimeVerticalModel,
  thumbnailHeight: Dp = 190.dp,
  onItemClick: () -> Unit
) {

  val painter = rememberImagePainter(
    data = anime.imageUrl,
    builder = {
      crossfade(true)
    }
  )


  Column(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable { onItemClick() }
  ) {
    Box(
      modifier = Modifier
        .height(thumbnailHeight)
    ) {
      if (painter.state is ImagePainter.State.Loading) {
        CenterCircularProgressIndicator(
          strokeWidth = 2.dp,
          size = 20.dp,
          color = MyColor.Yellow500
        )
      }
      Image(
        painter = painter,
        contentDescription = "Thumbnail",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxSize()
          .clip(RoundedCornerShape(12.dp))
      )
    }

    Text(
      text = anime.title,
      modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp),
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      style = TextStyle(
        color = MyColor.OnDarkSurface,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
      )
    )

    Text(
      text = "${anime.score}",
      modifier = Modifier.padding(bottom = 3.dp),
      style = TextStyle(
        color = MyColor.OnDarkSurface,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal
      )
    )
  }
}