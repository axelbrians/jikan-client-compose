package com.machina.jikan_client_compose.presentation.content_view_all_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllViewModel
import com.machina.jikan_client_compose.presentation.extension.isScrolledToTheEnd
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemAnimeTopShimmer
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemVerticalAnimeConfig
import com.machina.jikan_client_compose.ui.navigation.MainNavigation
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.valentinilk.shimmer.Shimmer
import timber.log.Timber

@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun ContentViewAllListScreen(
  modifier: Modifier = Modifier,
  navigation: MainNavigation.ContentViewAllScreenNavigation,
  viewModel: ContentViewAllViewModel
) {

  val shimmerInstance = rememberShimmerCustomBounds()
  val lazyGridState = rememberLazyListState()
  val contentState = viewModel.contentState.value

  LaunchedEffect(key1 = viewModel, block = { viewModel.getNextContentPart() })

  Scaffold() {
    LazyVerticalGrid(
      modifier = Modifier.onUpdateShimmerBounds(shimmerInstance),
      cells = GridCells.Fixed(3),
      state = lazyGridState,
      contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
      items(contentState.data) { data ->
        ItemVerticalAnime(
          modifier = ItemVerticalAnimeConfig.fillParentWidthModifier,
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

  if (lazyGridState.isScrolledToTheEnd()) { // fetch more item when scrolled to the end
    Timber.d("scrolled to the end, size ${contentState.data.size}")
    LaunchedEffect(key1 = contentState.data.size) {
      Timber.d("fetch more item, size ${contentState.data.size}")
      viewModel.getNextContentPart()
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyGridScope.showItemVerticalAnimeShimmer(
  shimmerInstance: Shimmer,
  count: Int = 5
) {
  items(count) {
    ItemAnimeTopShimmer(
      modifier = ItemVerticalAnimeConfig.fillParentWidthModifier,
      shimmerInstance = shimmerInstance,
      thumbnailHeight = 160.dp
    )
  }
}
