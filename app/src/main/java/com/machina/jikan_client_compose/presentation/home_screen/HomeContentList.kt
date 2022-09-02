package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.core.helper.DateHelper
import com.machina.jikan_client_compose.presentation.composable.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.composable.HorizontalContentHeaderConfig
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.AnimeAiringPopularHorizontalPager
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.state.AnimeAiringPopularState
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.presentation.home_screen.item.showItemVerticalAnimeMoreWhenPastLimit
import com.machina.jikan_client_compose.presentation.home_screen.item.showItemVerticalAnimeShimmer
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun HomeContentList(
  modifier: Modifier = Modifier,
  navigator: HomeScreenNavigator,
  animeAiringPopularState: AnimeAiringPopularState = AnimeAiringPopularState(),
  animeScheduleState: AnimeHorizontalListContentState = AnimeHorizontalListContentState(),
  animeTopState: AnimeHorizontalListContentState = AnimeHorizontalListContentState()
) {
  val lazyColumnState = rememberLazyListState()

  LazyColumn(
    modifier = modifier,
    state = lazyColumnState
  ) {

    /* - - - Start of Currently popular anime - - - */
    item(key = "horizontal_pager_demo") {
      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
      val pagerState = rememberPagerState()
      AnimeAiringPopularHorizontalPager(
        modifier = Modifier.onUpdateShimmerBounds(shimmerInstance),
        pagerState = pagerState,
        animeAiringPopularState = animeAiringPopularState,
        data = animeAiringPopularState.data.take(7),
        shimmerInstance = shimmerInstance,
        navigateToContentDetailsScreen = navigator::navigateToContentDetailsScreen
      )
    }
    /* End of Currently Popular Anime */


    /* Start of Anime Airing Today */
    item(key = "anime_schedule_list") {
      val shimmerInstance = rememberShimmerCustomBounds()
      val title = "Airing today"
      val direction = {
        navigator.navigateToContentViewAllScreen(
          title,
          Endpoints.ANIME_SCHEDULES + "/" + DateHelper.getTodayDayNameInString()
        )
      }

      if (animeScheduleState.isLoading) {
        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmerInstance)
      } else {
        HorizontalContentHeader(
          modifier = HorizontalContentHeaderConfig.default,
          title = title,
          onButtonClick = direction
        )
      }

      LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        modifier = Modifier.onUpdateShimmerBounds(shimmerInstance)
      ) {
        if (animeScheduleState.isLoading) {
          showItemVerticalAnimeShimmer(shimmerInstance)
        } else {
          items(animeScheduleState.data.data, key = { it.malId }) { data ->
            ItemVerticalAnime(
              modifier = ItemVerticalAnimeModifier.default,
              data = data,
              onClick = navigator::navigateToContentDetailsScreen
            )
          }
          showItemVerticalAnimeMoreWhenPastLimit(
            modifier = ItemVerticalAnimeModifier.default,
            size = animeScheduleState.data.data.size,
            onClick = direction
          )
        }
      }
    }
    // End of Anime Airing Today


    // Start of Top Anime of All Times
    item(key = "anime_top_list") {
      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
      val title = "Top anime of all times"
      val direction = {
        navigator.navigateToContentViewAllScreen(title, Endpoints.ANIME_TOP)
      }

      if (animeTopState.isLoading) {
        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmerInstance)
      } else {
        HorizontalContentHeader(
          modifier = HorizontalContentHeaderConfig.default,
          title = title,
          onButtonClick = direction
        )
      }

      LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        modifier = Modifier.onUpdateShimmerBounds(shimmerInstance)
      ) {
        if (animeTopState.isLoading) {
          showItemVerticalAnimeShimmer(shimmerInstance)
        } else {
          items(animeTopState.data.data, key = { item -> item.malId }) { model ->
            ItemVerticalAnime(
              modifier = ItemVerticalAnimeModifier.default,
              data = model,
              onClick = navigator::navigateToContentDetailsScreen
            )
          }
          showItemVerticalAnimeMoreWhenPastLimit(
            modifier = ItemVerticalAnimeModifier.default,
            size = animeTopState.data.data.size,
            onClick = direction
          )
        }
      }
    }
    // End of Top Anime of All Times


  }
}