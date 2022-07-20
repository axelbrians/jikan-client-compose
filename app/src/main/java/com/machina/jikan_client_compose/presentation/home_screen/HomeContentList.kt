package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.presentation.home_screen.anime_popular_current.AnimeAiringPopularHorizontalPager
import com.machina.jikan_client_compose.presentation.home_screen.anime_popular_current.state.AnimeAiringPopularState
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalContentState
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemAnimeSchedule
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemAnimeTopShimmer
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeScheduleState
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeTopState
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun HomeContentList(
  modifier: Modifier = Modifier,
  animeAiringPopularState: AnimeAiringPopularState = AnimeAiringPopularState(),
  animeScheduleState: AnimeHorizontalContentState = AnimeHorizontalContentState(),
  animeTopState: AnimeHorizontalContentState = AnimeHorizontalContentState(),
  lazyColumnState: LazyListState = rememberLazyListState(),
  onTopAnimeClick: (String, Int) -> Unit
) {

  LazyColumn(
    modifier = modifier,
    state = lazyColumnState
  ) {

    // Start of Currently popular anime
    item(key = "horizontal_pager_demo") {
      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
      val pagerState = rememberPagerState()
      val itemCount = animeAiringPopularState.data.size.coerceAtMost(7)
      AnimeAiringPopularHorizontalPager(
        modifier = Modifier
          .padding(top = 8.dp)
          .onGloballyPositioned { layoutCoordinates ->
            val position = layoutCoordinates.unclippedBoundsInWindow()
            shimmerInstance.updateBounds(position)
          },
        pagerState = pagerState,
        animeAiringPopularState = animeAiringPopularState,
        data = animeAiringPopularState.data.slice(0 until itemCount),
        shimmerInstance = shimmerInstance,
        onItemClick = onTopAnimeClick
      )
    }
    /* End of Currently Popular Anime */

    /* Start of Anime Airing Today */
    item(key = "anime_schedule_list") {
      HorizontalContentHeader(
        title = "Airing today",
        onButtonClick = { }
      )

      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
          val position = layoutCoordinates.unclippedBoundsInWindow()
          shimmerInstance.updateBounds(position)
        }
      ) {
        if (animeScheduleState.isLoading) {
          showShimmerPlaceholder(shimmerInstance)
        } else {
          items(animeScheduleState.data, key = { item -> item.malId }) { data ->
            ItemVerticalAnime(
              anime = data,
              onItemClick = { onTopAnimeClick(ContentType.Anime.name, data.malId) }
            )
          }
        }
      }
    }
    // End of Anime Airing Today


    // Start of Top Anime of All Times
    item(key = "anime_top_list") {
      HorizontalContentHeader(
        title = "Top Anime of All Times",
        onButtonClick = { }
      )

      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
          val position = layoutCoordinates.unclippedBoundsInWindow()
          shimmerInstance.updateBounds(position)
        }
      ) {
        if (animeTopState.isLoading) {
          showShimmerPlaceholder(shimmerInstance)
        } else {
          items(animeTopState.data, key = { item -> item.malId }) { data ->
            ItemVerticalAnime(
              anime = data,
              onItemClick = { onTopAnimeClick(ContentType.Anime.name, data.malId) }
            )
          }
        }

      }
    }
    // End of Top Anime of All Times


  }
}

@Composable
private fun HorizontalContentHeader(
  title: String,
  onButtonClick: () -> Unit
) {
  Row(
    modifier = Modifier.padding(start = 18.dp, end = 18.dp, bottom = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier.weight(1f),
      text = title,
      style = TextStyle(
        color = MyColor.Yellow500,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
      )
    )

    IconButton(onClick = onButtonClick) {
      Icon(
        imageVector = Icons.Default.ArrowForward,
        contentDescription = "See all",
        tint = MyColor.Grey
      )
    }
  }
}

private fun LazyListScope.showShimmerPlaceholder(shimmerInstance: Shimmer, count: Int = 5) {
  items(count) {
    ItemAnimeTopShimmer(shimmerInstance)
  }
}