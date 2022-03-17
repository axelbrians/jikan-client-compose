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
import com.machina.jikan_client_compose.presentation.home_screen.composable.ItemAnime
import com.machina.jikan_client_compose.presentation.home_screen.composable.ItemAnimeSchedule
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
  animeAiringPopularState: AnimeAiringPopularState = AnimeAiringPopularState(),
  animeScheduleState: AnimeScheduleState = AnimeScheduleState(),
  animeTopState: AnimeTopState = AnimeTopState(),
  lazyColumnState: LazyListState = rememberLazyListState(),
  onTopAnimeClick: (String, Int) -> Unit
) {

  LazyColumn(
    state = lazyColumnState
  ) {

    // Start of Currently popular anime
    item(key = "horizontal_pager_demo") {
      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
      val pagerState = rememberPagerState()
      val itemCount = animeAiringPopularState.data.size.coerceAtMost(7)
      AnimeAiringPopularHorizontalPager(
        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
          val position = layoutCoordinates.unclippedBoundsInWindow()
          shimmerInstance.updateBounds(position)
        },
        pagerState = pagerState,
        data = animeAiringPopularState.data.slice(0 until itemCount),
        shimmerInstance = shimmerInstance,
        onItemClick = onTopAnimeClick
      )
    }
    /* End of Currently Popular Anime */

    /* Start of Anime Airing Today */
    item(key = "anime_schedule_list") {
      Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          modifier = Modifier.weight(1f),
          text = "Airing today",
          style = TextStyle(
            color = MyColor.Yellow500,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
        )

        IconButton(onClick = { }) {
          Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "See all",
            tint = MyColor.Grey
          )
        }
      }


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
          items(animeScheduleState.data, key = { item -> item.malId }) { anime ->
            ItemAnimeSchedule(
              modifier = Modifier
                .width(160.dp)
                .padding(12.dp, 0.dp),
              anime = anime,
              onItemClick = { onTopAnimeClick(ContentType.Anime.name, anime.malId) }
            )
          }
        }
      }
    }
    // End of Anime Airing Today


    // Start of Top Anime of All Times
    item(key = "anime_top_list") {
      Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          modifier = Modifier.weight(1f),
          text = "Top Anime of All Times",
          style = TextStyle(
            color = MyColor.Yellow500,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
        )

        IconButton(onClick = { }) {
          Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "See all",
            tint = MyColor.Grey
          )
        }
      }

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
          items(animeTopState.data, key = { item -> item.malId }) { anime ->
            ItemAnime(
              modifier = Modifier
                .width(160.dp)
                .padding(12.dp, 0.dp),
              anime = anime,
              onItemClick = { onTopAnimeClick(ContentType.Anime.name, anime.malId) }
            )
          }
        }

      }
    }
    // End of Top Anime of All Times


  }
}

private fun LazyListScope.showShimmerPlaceholder(shimmerInstance: Shimmer, count: Int = 5) {
  items(count) {
    ItemAnimeTopShimmer(shimmerInstance)
  }
}