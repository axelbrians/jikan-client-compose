package com.machina.jikan_client_compose.view.detail_screen

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.machina.jikan_client_compose.data.response.ContentDetailsResponse
import com.machina.jikan_client_compose.ui.theme.*
import com.machina.jikan_client_compose.view.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.viewmodels.ContentDetailsViewModel
import me.onebone.toolbar.*

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun ContentDetailsScreen(
  navController: NavController,
  viewModel: ContentDetailsViewModel,
  contentType: String?,
  malId: Int?
) {
  val state = rememberCollapsingToolbarScaffoldState()
  val contentDetails: ContentDetailsResponse? by viewModel.contentDetails.observeAsState()
  val isFetching: Boolean by viewModel.isFetching.observeAsState(false)

  val coilPainter = rememberImagePainter(
    data = contentDetails?.imageUrl ?: "",
    builder = {
      crossfade(true)
    }
  )


  LaunchedEffect(
    key1 = contentType + malId,
    block = { viewModel.getContentDetails(contentType, malId) }
  )

  if (isFetching) {
    Scaffold(
      modifier = Modifier
        .fillMaxSize()
        .background(BlackBackground)
    ) {
      CenterCircularProgressIndicator(
        size = 40.dp,
        color = Yellow500
      )
    }
  } else if (contentDetails != null) {
    CollapsingToolbarScaffold(
      modifier = Modifier
        .fillMaxSize()
        .background(BlackBackground),
      state = state,
      scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
      toolbar = { ContentDetailsScreenToolbar(coilPainter = coilPainter, state = state, onArrowClick = { navController.navigateUp() })  }
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        var expanded by remember { mutableStateOf(false) }

        // Synopsis Composable

//        Surface(modifier = Modifier
//          .fillMaxWidth()
//          .padding(horizontal = 16.dp, vertical = 8.dp)
//          .clip(RoundedCornerShape(12.dp))
//          .background(BlackBlueBackground)
//        ) {
//
//        }
        AnimatedContent(
          targetState = expanded,
          transitionSpec = {
              expandVertically(animationSpec = tween(150, 150), initialHeight = { it }) with
              shrinkVertically(animationSpec = tween(150, 150), targetHeight = { it }) using
              SizeTransform(clip = true)
          }
        ) { targetExpanded ->
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 12.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(BlackBlueBackground)
          ) {
            Column {
              Text(
                text = "Synopsis",
                style = TextStyle(
                  color = OnDarkSurface,
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
              )
              if (targetExpanded) {
                Text(
                  text = contentDetails?.synopsis ?: "",
                  style = TextStyle(color = OnDarkSurface, fontSize = 14.sp),
                  modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                )
                IconButton(onClick = { expanded = !expanded }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                  Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Shrink", tint = Grey)
                }
              } else {
                Text(
                  text = contentDetails?.synopsis ?: "",
                  maxLines = 9,
                  overflow = TextOverflow.Ellipsis,
                  style = TextStyle(color = OnDarkSurface, fontSize = 14.sp),
                  modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                )
                IconButton(onClick = { expanded = !expanded }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                  Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Expand", tint = Grey)
                }
              }
            }
          }
        }
        repeat(7) {
          Text(
            text = "Anime Detail's",
            style = TextStyle(
              color = OnDarkSurface,
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
            ),
            modifier = Modifier.height(220.dp)
          )
        }
      }
    }
  }
}

@ExperimentalCoilApi
@Composable
fun CollapsingToolbarScope.ContentDetailsScreenToolbar(
  coilPainter: ImagePainter,
  state: CollapsingToolbarScaffoldState,
  onArrowClick: () -> Boolean
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(220.dp)
      .parallax(0.5f)
      .graphicsLayer {
        // change alpha of Image as the toolbar expands
        alpha = state.toolbarState.progress
      },
  ) {
    if (coilPainter.state is ImagePainter.State.Loading) {
      CenterCircularProgressIndicator(
        strokeWidth = 2.dp,
        size = 20.dp,
        color = Yellow500
      )
    }
    Image(
      painter = coilPainter,
      contentDescription = "Thumbnail",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .fillMaxSize()
    )
  }

  IconButton(onClick = { onArrowClick() }) {
    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Grey)
  }
}