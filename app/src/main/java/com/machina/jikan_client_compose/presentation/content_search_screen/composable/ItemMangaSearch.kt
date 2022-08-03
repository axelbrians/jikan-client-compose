package com.machina.jikan_client_compose.presentation.content_search_screen.composable

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.ContentSearch
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.ui.theme.MyColor

@ExperimentalCoilApi
@Composable
fun ItemMangaSearch(
  modifier: Modifier = Modifier,
  data: ContentSearch,
  onItemClick: (Int, ContentType) -> Unit
) {

  val painter = rememberImagePainter(
    data = data.imageUrl,
    builder = {
      crossfade(true)
    }
  )

  Row(
    modifier = modifier
      .clickable { onItemClick(data.malId, ContentType.Manga) }
  ) {
    Box(
      modifier = Modifier
        .width(120.dp)
        .height(160.dp)
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

    Column(
      modifier = Modifier
        .weight(1f)
        .height(160.dp)
        .padding(8.dp, 0.dp, 0.dp, 4.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = data.title,
          style = TextStyle(
            color = MyColor.OnDarkSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          ),
          modifier = Modifier.padding(top = 2.dp)
        )

        Text(
          text = data.synopsis,
          maxLines = 4,
          overflow = TextOverflow.Ellipsis,
          style = TextStyle(
            color = MyColor.OnDarkSurface,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
          ),
          modifier = Modifier
            .padding(bottom = 3.dp)
        )
      }

      Column {
        Text(
          text = "${data.score}",
          style = TextStyle(
            color = MyColor.OnDarkSurface,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
          ),
          modifier = Modifier.padding(bottom = 2.dp)
        )

        if (data.chapters != null && data.chapters > 0) {
          Text(
            text = "${data.chapters} chapters",
            style = TextStyle(
              color = MyColor.OnDarkSurface,
              fontSize = 13.sp,
              fontWeight = FontWeight.Normal
            ),
//                        modifier = Modifier.padding(bottom = 2.dp)
          )

          Text(
            text = "${data.volumes} volumes",
            style = TextStyle(
              color = MyColor.OnDarkSurface,
              fontSize = 13.sp,
              fontWeight = FontWeight.Normal
            ),
//                        modifier = Modifier.padding(bottom = 2.dp)
          )
        } else {
          Text(
            text = "publishing",
            style = TextStyle(
              color = MyColor.OnDarkSurface,
              fontSize = 13.sp,
              fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(bottom = 2.dp)
          )
        }
      }
    }
  }
}