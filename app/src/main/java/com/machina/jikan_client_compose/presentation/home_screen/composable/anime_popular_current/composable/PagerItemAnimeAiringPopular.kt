package com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlin.math.absoluteValue

@OptIn(ExperimentalCoilApi::class, ExperimentalPagerApi::class)
@Composable
fun PagerScope.PagerItemAnimeAiringPopular(
  modifier: Modifier = Modifier,
  data: AnimeAiringPopular,
  currentPage: Int,
  painter: ImagePainter,
  onClick: () -> Unit
) {

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
          start = 0.85f,
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
          .padding(horizontal = 12.dp, vertical = 6.dp)
          .background(
            color = MyColor.DarkBlueBackground,
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
}