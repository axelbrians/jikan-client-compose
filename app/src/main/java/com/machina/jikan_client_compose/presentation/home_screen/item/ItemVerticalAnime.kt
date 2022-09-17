package com.machina.jikan_client_compose.presentation.home_screen.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.home_screen.preview.ItemVerticalAnimeProvider
import com.machina.jikan_client_compose.presentation.home_screen.preview.ItemVerticalAnimeState
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.machina.jikan_client_compose.ui.theme.MyType

@ExperimentalCoilApi
@Composable
fun ItemVerticalAnime(
  modifier: Modifier = Modifier,
  data: AnimeVerticalDataModel,
  thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightDefault,
  textAlign: TextAlign = TextAlign.Start,
  onClick: (Int, ContentType) -> Unit
) {
//  var titleLineCount by remember { mutableStateOf(0) }
  val painter = rememberImagePainter(
    data = data.imageUrl,
    builder = {
      crossfade(true)
    }
  )

  Column(
    modifier = modifier
      .clip(MyShape.Rounded12)
      .clickable { onClick(data.malId, ContentType.Anime) }
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
      val imageModifier = Modifier
        .fillMaxSize()
        .clip(MyShape.Rounded12)
      if (LocalInspectionMode.current) {
        Box(modifier = imageModifier
          .background(MyColor.Teal200)
        )
      } else {
        Image(
          painter = painter,
          contentDescription = "Thumbnail",
          contentScale = ContentScale.Crop,
          modifier = imageModifier
        )
      }
    }

    Text(
      text = data.title,
      modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      style = MyType.Body2.Normal.OnDarkSurfaceLight,
      textAlign = textAlign,
//      onTextLayout = {
//        titleLineCount = it.lineCount
//      }
    )

//    repeat(2 - titleLineCount) {
//      Text(
//        text = "",
//        style = MyType.Body2.Bold.OnDarkSurface
//      )
//    }
//
//    Text(
//      text = "${data.score}",
//      modifier = Modifier.padding(bottom = 3.dp),
//      style = TextStyle(
//        color = MyColor.OnDarkSurface,
//        fontSize = 13.sp,
//        fontWeight = FontWeight.Normal
//      )
//    )
  }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
fun Preview_ItemVerticalAnime_Default(
  @PreviewParameter(ItemVerticalAnimeProvider::class) data: ItemVerticalAnimeState,
) {
  ItemVerticalAnime(
    modifier = data.modifier,
    data = data.data,
    thumbnailHeight = data.thumbnailHeight,
    onClick = { _, _ -> }
  )
}