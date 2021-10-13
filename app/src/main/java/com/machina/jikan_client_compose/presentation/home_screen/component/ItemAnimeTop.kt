package com.machina.jikan_client_compose.presentation.home_screen

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.machina.jikan_client_compose.domain.model.AnimeTop
import com.machina.jikan_client_compose.ui.theme.OnDarkSurface
import com.machina.jikan_client_compose.ui.theme.Yellow500
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator


@ExperimentalCoilApi
@Composable
fun ItemAnime(
  modifier: Modifier = Modifier,
  anime: AnimeTop,
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
        .height(190.dp)
    ) {
      if (painter.state is ImagePainter.State.Loading) {
        CenterCircularProgressIndicator(
          strokeWidth = 2.dp,
          size = 20.dp,
          color = Yellow500
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
      style = TextStyle(color = OnDarkSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold),
      modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp)
    )

    Text(
      text = "${anime.score}",
      style = TextStyle(color = OnDarkSurface, fontSize = 14.sp, fontWeight = FontWeight.Normal),
      modifier = Modifier
        .padding(bottom = 3.dp)
    )
  }
}