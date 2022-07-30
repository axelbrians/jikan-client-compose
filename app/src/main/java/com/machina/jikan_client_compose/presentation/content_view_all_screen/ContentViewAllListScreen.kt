package com.machina.jikan_client_compose.presentation.content_view_all_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.content_view_all_screen.composable.ContentViewAllListScreenToolbar
import com.machina.jikan_client_compose.presentation.content_view_all_screen.data.ScrollDirection
import com.machina.jikan_client_compose.presentation.content_view_all_screen.data.rememberLazyScrollDirection
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllViewModel
import com.machina.jikan_client_compose.presentation.extension.isScrolledToTheEnd
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemAnimeTopShimmer
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemVerticalAnimeConfig
import com.machina.jikan_client_compose.ui.animation_spec.TweenSpec
import com.machina.jikan_client_compose.ui.navigation.navigator.ContentViewAllScreenNavigation
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer

@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun ContentViewAllListScreen(
  modifier: Modifier = Modifier,
  navigation: ContentViewAllScreenNavigation,
  viewModel: ContentViewAllViewModel,
  title: String
) {

  val contentState = viewModel.contentState.value
  val dataSet = contentState.data.data
  val shimmerInstance = rememberShimmerCustomBounds()
  val lazyGridState = rememberLazyListState()
  val scrollDirection = rememberLazyScrollDirection()
  val direction = scrollDirection.getScrollDirection(
      lazyGridState.firstVisibleItemScrollOffset,
      lazyGridState.firstVisibleItemIndex
    )
  val animateToolbarOffset = animateDpAsState(
    targetValue = if (direction == ScrollDirection.UP) 0.dp else (-56).dp,
    animationSpec = TweenSpec.defaultEasing()
  )


  LaunchedEffect(key1 = viewModel, block = { viewModel.getNextContentPart() })

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MyColor.BlackBackground)
  ) {
    ContentViewAllListScreenToolbar(
      modifier = Modifier
        .offset(y = animateToolbarOffset.value)
        .zIndex(2f),
      title = title,
      onClick = navigation::navigateUp
    )
    LazyVerticalGrid(
      modifier = Modifier
        .fillMaxSize()
        .onUpdateShimmerBounds(shimmerInstance),
      cells = GridCells.Fixed(3),
      state = lazyGridState,
      contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
      itemsIndexed(dataSet) { index: Int, data: AnimeVerticalDataModel ->
        var itemModifier = ItemVerticalAnimeConfig.fillParentWidthModifier

        if (index in 0 until 3) {
          itemModifier = itemModifier.padding(top = 56.dp - 12.dp)
        }
        ItemVerticalAnime(
          modifier = itemModifier,
          data = data,
          thumbnailHeight = 160.dp,
          navigateToContentDetailsScreen = navigation::navigateToContentDetailsScreen
        )
      }

      if (contentState.isLoading) {
        showItemVerticalAnimeShimmer(shimmerInstance)
      }
    }
  }

  if (lazyGridState.isScrolledToTheEnd() && viewModel.hasNextContentPart()) { // fetch more item when scrolled to the end
    viewModel.getNextContentPart()
  }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyGridScope.showItemVerticalAnimeShimmer(
  shimmerInstance: Shimmer,
  count: Int = 9
) {
  items(count) {
    ItemAnimeTopShimmer(
      modifier = ItemVerticalAnimeConfig.fillParentWidthModifier,
      shimmerInstance = shimmerInstance,
      thumbnailHeight = 160.dp
    )
  }
}
