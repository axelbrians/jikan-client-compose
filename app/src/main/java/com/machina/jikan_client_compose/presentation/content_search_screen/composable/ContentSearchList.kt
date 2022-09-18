package com.machina.jikan_client_compose.presentation.content_search_screen.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.shimmer.ItemAnimeSearchShimmer
import com.machina.jikan_client_compose.presentation.content_search_screen.data.ContentSearchState
import com.machina.jikan_client_compose.presentation.content_search_screen.item.ItemAnimeSearch
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@ExperimentalCoilApi
@Composable
fun ContentSearchList(
  listState: LazyListState,
  state: ContentSearchState,
  onItemClick: (Int, ContentType) -> Unit
) {
  val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

  LazyColumn(
    modifier = Modifier.onUpdateShimmerBounds(shimmerInstance),
    state = listState,
  ) {
    itemsIndexed(state.data.data, key = { index, data ->
      "${data.malId}-$index"
    }) { _, data ->
//      when (data.type) {
//        MANGA, MANHUA, MANHWA, DOUJIN, ONE_SHOT, LIGHT_NOVEL -> {
//          ItemMangaSearch(
//            modifier = Modifier
//              .fillMaxWidth()
//              .padding(12.dp, 8.dp),
//            data = data,
//            onItemClick = onItemClick
//          )
//        }
//        else -> {
          ItemAnimeSearch(
            modifier = Modifier,
            data = data,
            onItemClick = onItemClick
          )
//        }
//      }

    }

    if (state.isLoading) {
      items(4) {
        ItemAnimeSearchShimmer(shimmerInstance)
      }
    }

    val error = state.error.peekContent()
    if (error != null) {
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
        ) {
          Text(
            text = error,
            color = MyColor.OnDarkSurface,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }
  }
}