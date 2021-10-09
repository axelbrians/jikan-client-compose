package com.machina.jikan_client_compose.view.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.core.enum.ContentType.Manga
import com.machina.jikan_client_compose.data.model.ContentSearch
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@ExperimentalCoilApi
@Composable
fun AnimeSearchList(
    navController: NavController,
    listState: LazyListState,
    dataSet: List<ContentSearch>,
    isFetching: Boolean
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
        itemsIndexed(dataSet, key = { index, data ->
            "${data.malId}-$index"
        }) { _, data ->
            when(data.type) {
                Manga.name -> {
                    ItemMangaSearch(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 8.dp),
                        data = data
                    )
                }
                else -> {
                    ItemAnimeSearch(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 8.dp),
                        data = data
                    )
                }
            }

        }

        if (isFetching) {
            items(4) {
                ItemAnimeSearchShimmer(shimmerInstance)
            }
        }
    }
}