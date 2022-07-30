package com.machina.jikan_client_compose.presentation.content_detail_screen.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsState
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons
import com.machina.jikan_client_compose.ui.theme.MySize
import me.onebone.toolbar.*
import kotlin.math.roundToInt

@ExperimentalCoilApi
@Composable
fun CollapsingToolbarScope.ContentDetailsScreenToolbar(
  largeCoil: ImagePainter = rememberImagePainter(data = null),
  smallCoil: ImagePainter = rememberImagePainter(data = null),
  contentDetailsState: ContentDetailsState = ContentDetailsState(null),
  toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
  onArrowClick: () -> Boolean = { false }
) {
  val blockerColorGradients = listOf(
    MyColor.BlackBackground.copy(alpha = 0.8F),
    MyColor.BlackBackground.copy(alpha = 0.9F),
    MyColor.BlackBackground
  )

  val isTitleVisible = toolbarScaffoldState.toolbarState.progress <= 0.25


  val headerCaptionIcon: ImageVector
  val headerCaptionDescription: String

  if (
    contentDetailsState.data?.isAiring == true ||
    contentDetailsState.data?.isPublishing == true
  ) {
    headerCaptionIcon = ImageVector.vectorResource(id = MyIcons.Outlined.Clock4)
    headerCaptionDescription = "Ongoing"
  } else {
    headerCaptionIcon = ImageVector.vectorResource(id = MyIcons.Outlined.DoubleCheck)
    headerCaptionDescription = "Completed"
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(240.dp)
      .parallax(0.5f)
      .graphicsLayer {
        // change alpha of Image as the toolbar expands
        alpha = toolbarScaffoldState.toolbarState.progress
      },
  ) {

    // Parallax header background
    Box {
      Image(
        painter = largeCoil,
        contentDescription = "Heading Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxSize()
      )
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            brush = Brush.verticalGradient(colors = blockerColorGradients)
          )
      )

      // Header Content
      Row(
        modifier = Modifier
          .fillMaxSize()
          .statusBarsPadding()
          .then(Modifier.padding(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)),
        verticalAlignment = Alignment.CenterVertically
      ) {

        // Left cover image
        Box(
          modifier = Modifier
            .width(100.dp)
        ) {
          if (smallCoil.state is ImagePainter.State.Loading) {
            CenterCircularProgressIndicator(
              strokeWidth = 2.dp,
              size = 20.dp,
              color = MyColor.Yellow500
            )
          }
          Image(
            painter = smallCoil,
            contentDescription = "Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .fillMaxSize()
              .clip(RoundedCornerShape(8.dp))
          )
        }

        // Header right content
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
        ) {
          Text(
            text = contentDetailsState.data?.title ?: "-",
            style = TextStyle(
              color = MyColor.OnDarkSurfaceLight,
              fontWeight = FontWeight.Bold,
              fontSize = MySize.Text20
            )
          )

          with(contentDetailsState.data) {
            this?.authors?.firstOrNull()?.let { author ->
              Text(
                text = author.name,
                style = TextStyle(
                  color = MyColor.OnDarkSurface,
                  fontWeight = FontWeight.Bold,
                  fontSize = MySize.Text14
                )
              )
            }

            this?.studios?.firstOrNull()?.let { studio ->
              Text(
                text = studio.name,
                style = TextStyle(
                  color = MyColor.OnDarkSurface,
                  fontWeight = FontWeight.Bold,
                  fontSize = MySize.Text14
                )
              )
            }
          }

          // Ongoing / Airing status
          Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
              imageVector = headerCaptionIcon,
              contentDescription = headerCaptionDescription,
              tint = MyColor.OnDarkSurface,
              modifier = Modifier
                .height(14.dp)
                .padding(end = 6.dp))

            Text(
              text = contentDetailsState.data?.status ?: "-",
              style = TextStyle(
                color = MyColor.OnDarkSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            )
          }
        }
      }
    }
  }


  // Toolbar
  Row(
    modifier = Modifier.fillMaxWidth().statusBarsPadding(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(onClick = { onArrowClick() }) {
      Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back", tint = MyColor.OnDarkSurfaceLight
      )
    }

    val density = LocalDensity.current
    val initialOffset = with(density) {
      40.dp.toPx().roundToInt()
    }
    val targetOffset = with(density) {
      -40.dp.toPx().roundToInt()
    }

    AnimatedVisibility(
      visible = isTitleVisible,
      enter = slideInVertically(
        initialOffsetY = { initialOffset },
        animationSpec = tween(
          durationMillis = 800,
          delayMillis = 50,
          easing = FastOutSlowInEasing
        )
      ) + fadeIn(initialAlpha = 0f),
      exit = slideOutVertically(
        targetOffsetY = { targetOffset },
        animationSpec = tween(
          durationMillis = 800,
          delayMillis = 50,
          easing = LinearOutSlowInEasing
        )
      ) + fadeOut()
    ) {
      Text(
        text = contentDetailsState.data?.title ?: "-",
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = TextStyle(
          color = MyColor.OnDarkSurfaceLight,
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        ),
        modifier = Modifier.weight(1f).padding(start = 8.dp, end = 12.dp)
      )
    }
  }
}



@OptIn(ExperimentalCoilApi::class)
@Preview(widthDp = 280)
@Composable
fun Preview_ContentDetailsScreenCollapsingToolbar() {
  CollapsingToolbarScaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(MyColor.BlackBackground),
    state = rememberCollapsingToolbarScaffoldState(),
    scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
    toolbar = {
      ContentDetailsScreenToolbar()
    }
  ) {

  }
}