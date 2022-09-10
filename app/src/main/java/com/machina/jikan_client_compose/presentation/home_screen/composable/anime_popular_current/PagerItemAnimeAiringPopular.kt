package com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.bold
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface
import kotlin.math.absoluteValue

@OptIn(ExperimentalCoilApi::class, ExperimentalPagerApi::class)
@Composable
fun PagerScope.PagerItemAnimeAiringPopular(
  modifier: Modifier = Modifier,
  data: AnimeAiringPopular,
  currentPage: Int,
  onClick: () -> Unit
) {
  val painter = rememberImagePainter(data = data.imageUrl)

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
          start = 0.9f,
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
      modifier = modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(12.dp))
        .clickable { onClick() }
    ) {
      if (painter.state is ImagePainter.State.Loading) {
        CenterCircularProgressIndicator(
          strokeWidth = 2.dp,
          size = 20.dp,
          color = MyColor.Yellow500
        )
      } else {
        Image(
          painter = painter,
          contentDescription = "Thumbnail",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
        )
      }

      /* pager item title */
      Box(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .background(
            brush = Brush.verticalGradient(
              colors = listOf(
                MyColor.DarkBlueBackground.copy(alpha = 0F),
                MyColor.DarkBlueBackground.copy(alpha = 0.9F),
                MyColor.DarkBlueBackground
              )
            )
          )
          .padding(horizontal = 12.dp, vertical = 6.dp)
          .zIndex(2f)
      ) {
        Text(
          text = data.title,
          maxLines = 1,
          textAlign = TextAlign.Center,
          overflow = TextOverflow.Ellipsis,
          style = Type.Typography.subtitle2.onDarkSurface().bold(),
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        )
      }
    }
  }
}