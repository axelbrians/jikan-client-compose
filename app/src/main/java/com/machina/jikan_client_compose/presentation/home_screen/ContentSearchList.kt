package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.enum.MangaSubType.DOUJIN
import com.machina.jikan_client_compose.core.enum.MangaSubType.LIGHT_NOVEL
import com.machina.jikan_client_compose.core.enum.MangaSubType.MANGA
import com.machina.jikan_client_compose.core.enum.MangaSubType.MANHUA
import com.machina.jikan_client_compose.core.enum.MangaSubType.MANHWA
import com.machina.jikan_client_compose.core.enum.MangaSubType.ONE_SHOT
import com.machina.jikan_client_compose.presentation.home_screen.component.ItemAnimeSearch
import com.machina.jikan_client_compose.presentation.home_screen.component.ItemAnimeSearchShimmer
import com.machina.jikan_client_compose.presentation.home_screen.component.ItemMangaSearch
import com.machina.jikan_client_compose.presentation.home_screen.data.ContentSearchState
import com.machina.jikan_client_compose.ui.theme.OnDarkSurface
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@ExperimentalCoilApi
@Composable
fun ContentSearchList(
  listState: LazyListState,
  state: ContentSearchState,
  onItemClick: (String, Int) -> Unit
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
            val position = layoutCoordinates.unclippedBoundsInWindow()
            shimmerInstance.updateBounds(position)
        }
    ) {
        itemsIndexed(state.data, key = { index, data ->
            "${data.malId}-$index"
        }) { _, data ->
            when(data.type) {
                MANGA, MANHUA, MANHWA, DOUJIN, ONE_SHOT, LIGHT_NOVEL -> {
                    ItemMangaSearch(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 8.dp),
                        data = data,
                        onItemClick = onItemClick
                    )
                }
                else -> {
                    ItemAnimeSearch(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 8.dp),
                        data = data,
                        onItemClick = onItemClick
                    )
                }
            }

        }

        if (state.isLoading) {
            items(4) {
                ItemAnimeSearchShimmer(shimmerInstance)
            }
        }

        if (state.error != null) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(text = state.error, color = OnDarkSurface, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}