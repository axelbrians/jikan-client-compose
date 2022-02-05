package com.machina.jikan_client_compose.presentation.detail_screen.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.detail_screen.data.ContentDetailsState
import com.machina.jikan_client_compose.ui.theme.*
import me.onebone.toolbar.*

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


  val headerCaptionIcon: ImageVector
  val headerCaptionDescription: String

  if (contentDetailsState.data?.isAiring == true
    || contentDetailsState.data?.isPublishing == true
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
      .height(220.dp)
      .parallax(0.5f)
      .graphicsLayer {
        // change alpha of Image as the toolbar expands
        alpha = toolbarScaffoldState.toolbarState.progress
      },
  ) {
//    if (largeCoil.state is ImagePainter.State.Loading) {
//      CenterCircularProgressIndicator(
//        strokeWidth = 2.dp,
//        size = 20.dp,
//        color = MyColor.Yellow500
//      )
//    }
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

      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
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

  IconButton(onClick = { onArrowClick() }) {
    Icon(
      imageVector = Icons.Default.ArrowBack,
      contentDescription = "Back", tint = MyColor.OnDarkSurface
    )
  }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(widthDp = 280)
@Composable
fun ContentDetailsScreenCollapsingToolbarPrev() {
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