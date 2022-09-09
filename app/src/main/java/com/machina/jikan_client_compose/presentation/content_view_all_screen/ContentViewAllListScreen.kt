package com.machina.jikan_client_compose.presentation.content_view_all_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.extensions.isScrolledToTheEnd
import com.machina.jikan_client_compose.core.extensions.scrollDirection
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.content_view_all_screen.composable.ContentViewAllListScreenToolbar
import com.machina.jikan_client_compose.presentation.content_view_all_screen.data.ScrollDirection
import com.machina.jikan_client_compose.presentation.content_view_all_screen.nav.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_screen.nav.ContentViewAllScreenNavigator
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllAnimeViewModel
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.showItemVerticalAnimeShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.ui.animation_spec.TweenSpec
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor

@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun ContentViewAllListScreen(
  navigator: ContentViewAllScreenNavigator,
  viewModel: ContentViewAllAnimeViewModel,
  navArgs: ContentViewAllListNavArgs
) {

  val contentState = viewModel.contentState.value
  val dataSet = contentState.data.data
  val shimmerInstance = rememberShimmerCustomBounds()
  val lazyGridState = rememberLazyListState()
  val animateToolbarOffset = animateDpAsState(
    targetValue = if (lazyGridState.scrollDirection() == ScrollDirection.UP) 0.dp else (-56).dp,
    animationSpec = TweenSpec.defaultEasing()
  )

  LaunchedEffect(key1 = viewModel) {
    viewModel.getNextContentPart(navArgs.url, navArgs.params)
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MyColor.DarkBlueBackground)
  ) {
    ContentViewAllListScreenToolbar(
      modifier = Modifier
        .offset(y = animateToolbarOffset.value)
        .zIndex(2f),
      title = navArgs.title,
      onClick = navigator::navigateUp
    )
    LazyVerticalGrid(
      modifier = Modifier
        .fillMaxSize()
        .onUpdateShimmerBounds(shimmerInstance),
      cells = GridCells.Fixed(3),
      state = lazyGridState,
      contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 64.dp, bottom = 12.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      items(dataSet) { data: AnimeVerticalDataModel ->
        ItemVerticalAnime(
          modifier = ItemVerticalAnimeModifier.fillParentWidth,
          data = data,
          thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightGrid,
          onClick = navigator::navigateToContentDetailsScreen
        )
      }

      if (contentState.isLoading) {
        showItemVerticalAnimeShimmer(
          shimmerInstance = shimmerInstance,
          thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightGrid
        )
      }
    }
  }

  if (lazyGridState.isScrolledToTheEnd() && viewModel.hasNextContentPart()) { // fetch more item when scrolled to the end
    viewModel.getNextContentPart(navArgs.url, navArgs.params)
  }
}