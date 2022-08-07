package com.machina.jikan_client_compose.presentation.content_detail_screen.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.machina.jikan_client_compose.domain.model.anime.AnimeCharacterModel
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.alignCenter
import com.machina.jikan_client_compose.ui.theme.Type.bold
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface

object ItemAnimeCharacterConfig {
  val default = Modifier
    .width(100.dp)
    .padding(6.dp, 4.dp)
}

@ExperimentalCoilApi
@Composable
fun ItemAnimeCharacter(
  modifier: Modifier = Modifier,
  data: AnimeCharacterModel
) {

  val painter = rememberImagePainter(
    data = data.imageUrl,
    builder = {
      crossfade(true)
    }
  )

  Column(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable {  }
  ) {
    Box(
      modifier = Modifier
        .height(120.dp)
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
      text = data.name,
      modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      style = Type.Typography.body2.onDarkSurface().bold().alignCenter()
    )
  }
}