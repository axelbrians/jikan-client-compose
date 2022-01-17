package com.machina.jikan_client_compose.presentation.detail_screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.detail_screen.composable.ContentDetailsScreenToolbar
import com.machina.jikan_client_compose.presentation.detail_screen.composable.ContentDetailsSynopsis
import com.machina.jikan_client_compose.presentation.detail_screen.data.ContentDetailsState
import com.machina.jikan_client_compose.presentation.detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.ui.theme.MyColor
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import timber.log.Timber

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun ContentDetailsScreen(
  viewModel: ContentDetailsViewModel,
  contentType: String?,
  malId: Int?,
  onBackPressed: () -> Boolean = { false }
) {
  val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
  val contentDetailsState = viewModel.contentDetailsState.value
  val genres = contentDetailsState.data?.genres ?: listOf()

  val coilPainter = rememberImagePainter(
    data = contentDetailsState.data?.imageUrl,
    builder = {
      crossfade(true)
    }
  )

  LaunchedEffect(
    key1 = contentType + malId,
    block = { viewModel.getContentDetails(contentType, malId) }
  )


  CollapsingToolbarScaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(MyColor.BlackBackground),
    state = toolbarScaffoldState,
    scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
    toolbar = {
      ContentDetailsScreenToolbar(
        coilPainter = coilPainter,
        contentDetailsState = contentDetailsState,
        toolbarScaffoldState = toolbarScaffoldState,
        onArrowClick = onBackPressed
      )
    }
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalAlignment = Alignment.Start,
    ) {
      // Genre FlowRow Chips
      item(key = "content_genre_chips") {
        if (genres.isNotEmpty()) {
          LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Start
          ) {
            this.items(genres) { genre ->
              Surface(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp),
                shape = RoundedCornerShape(16.dp),
                color = MyColor.Yellow500,
              ) {
                Text(
                  text = genre.name,
                  style = TextStyle(
                    color = MyColor.BlackBackground,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                  ),
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }

      // Synopsis Composable
      item(key = "content_description_composable") {
        ContentDetailsSynopsis(state = contentDetailsState)
      }

      items(7) {
        Text(
          text = "Anime Detail's",
          style = TextStyle(
            color = MyColor.OnDarkSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
          ),
          modifier = Modifier.height(220.dp)
        )
      }
    }
  }


  if (contentDetailsState.isLoading) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .background(MyColor.BlackBackground)
    ) {
      CenterCircularProgressIndicator(
        size = 40.dp,
        color = MyColor.Yellow500
      )
    }
  }
}