package com.machina.jikan_client_compose.presentation.detail_screen

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.detail_screen.header.ContentDetailsScreenToolbar
import com.machina.jikan_client_compose.presentation.detail_screen.synopsis.ContentDetailsSynopsis
import com.machina.jikan_client_compose.presentation.detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.presentation.detail_screen.three_column.ContentDetailsThreeColumnSection
import com.machina.jikan_client_compose.presentation.detail_screen.trailer_player.ContentDetailsTrailerPlayer
import com.machina.jikan_client_compose.ui.theme.MyColor
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.roundToInt

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

  val largeImageCoil = rememberImagePainter(
    data = contentDetailsState.data?.images?.jpg?.largeImageUrl,
    builder = { crossfade(true) }
  )

  val smallImageCoil = rememberImagePainter(
    data = contentDetailsState.data?.images?.jpg?.imageUrl,
    builder = { crossfade(true) }
  )

  LaunchedEffect(
    key1 = contentType + malId,
    block = { viewModel.getContentDetails(contentType, malId) }
  )

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
  } else {
    if (contentDetailsState.data != null) {
      CollapsingToolbarScaffold(
        modifier = Modifier
          .fillMaxSize()
          .background(MyColor.BlackBackground),
        state = toolbarScaffoldState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
          ContentDetailsScreenToolbar(
            largeCoil = largeImageCoil,
            smallCoil = smallImageCoil,
            contentDetailsState = contentDetailsState,
            toolbarScaffoldState = toolbarScaffoldState,
            onArrowClick = onBackPressed
          )
        }
      ) {
        SwipeRefresh(
          state = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.value),
          onRefresh = { viewModel.getContentDetails(contentType, malId, true) }
        ) {
          LazyColumn(
            modifier = Modifier
              .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
          ) {

            // Three Column Section
            item(key = "three_column_section") {
              ContentDetailsThreeColumnSection(
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                state = contentDetailsState)
            }


            // Synopsis Composable
            item(key = "content_description_composable") {
              ContentDetailsSynopsis(state = contentDetailsState)
            }


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

            // Content Trailer (if any, like TV or Movies or Anime
            contentDetailsState.data?.trailer?.embedUrl?.let {
              item(key = "content_trailer") {
                ContentDetailsTrailerPlayer(
                  modifier = Modifier
                    .padding(top = 12.dp)
                    .height(240.dp),
                  it
                )
              }
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
      }
    } else {
      SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.value),
        onRefresh = { viewModel.getContentDetails(contentType, malId, true) }
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .background(MyColor.BlackBackground)
            .verticalScroll(state = rememberScrollState())
        ) {
          Row(
            modifier = Modifier.fillMaxWidth().statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            IconButton(onClick = { onBackPressed() }) {
              Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back", tint = MyColor.OnDarkSurfaceLight
              )
            }
            Text(
              text = "",
              overflow = TextOverflow.Ellipsis,
              maxLines = 1,
              style = TextStyle(
                color = MyColor.OnDarkSurfaceLight,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
              ),
              modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 12.dp)
            )
          }
        }
      }
    }
  }
}