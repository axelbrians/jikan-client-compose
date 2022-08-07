package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.machina.jikan_client_compose.presentation.composable.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.composable.HorizontalContentHeaderConfig
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.AnimeAiringPopularHorizontalPager
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.state.AnimeAiringPopularState
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ItemVerticalAnimeShimmer
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeConfig
import com.machina.jikan_client_compose.ui.navigation.content_view_all.ContentViewAllType
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun HomeContentList(
  modifier: Modifier = Modifier,
  navigation: HomeScreenNavigator,
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
      val itemCount = animeAiringPopularState.data.size.coerceAtMost(7)
      AnimeAiringPopularHorizontalPager(
        modifier = Modifier
          .padding(top = 8.dp)
          .onUpdateShimmerBounds(shimmerInstance),
        pagerState = pagerState,
        animeAiringPopularState = animeAiringPopularState,
        data = animeAiringPopularState.data.slice(0 until itemCount),
        shimmerInstance = shimmerInstance,
        navigateToContentDetailsScreen = navigation::navigateToContentDetailsScreen
      )
    }
    /* End of Currently Popular Anime */


    /* Start of Anime Airing Today */
    item(key = "anime_schedule_list") {
      val shimmerInstance = rememberShimmerCustomBounds()
      val title = "Airing today"

      if (animeScheduleState.isLoading) {
        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmerInstance)
      } else {
        HorizontalContentHeader(
          modifier = HorizontalContentHeaderConfig.default,
          title = title,
          onButtonClick = {
            navigation.navigateToContentViewAllScreen(ContentViewAllType.AnimeSchedule, title)
          }
        )
      }

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
        modifier = Modifier.onUpdateShimmerBounds(shimmerInstance)
      ) {
        if (animeScheduleState.isLoading) {
          showItemAnimeTopShimmer(shimmerInstance)
        } else {
          items(animeScheduleState.data.data, key = { it.malId }) { data ->
            ItemVerticalAnime(
              modifier = ItemVerticalAnimeConfig.defaultModifier,
              data = data,
              navigateToContentDetailsScreen = navigation::navigateToContentDetailsScreen
            )
          }
        }
      }
    }
    // End of Anime Airing Today


    // Start of Top Anime of All Times
    item(key = "anime_top_list") {
      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
      val title = "Top anime of all times"

      if (animeTopState.isLoading) {
        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmerInstance)
      } else {
        HorizontalContentHeader(
          modifier = HorizontalContentHeaderConfig.default,
          title = title,
          onButtonClick = {
            navigation.navigateToContentViewAllScreen(ContentViewAllType.AnimeTop, title)
          }
        )
      }

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
        modifier = Modifier.onUpdateShimmerBounds(shimmerInstance)
      ) {
        if (animeTopState.isLoading) {
          showItemAnimeTopShimmer(shimmerInstance)
        } else {
          items(animeTopState.data.data, key = { item -> item.malId }) { model ->
            ItemVerticalAnime(
              modifier = ItemVerticalAnimeConfig.defaultModifier,
              data = model,
              navigateToContentDetailsScreen = navigation::navigateToContentDetailsScreen
            )
          }
        }
      }
    }
    // End of Top Anime of All Times


  }
}

private fun LazyListScope.showItemAnimeTopShimmer(shimmerInstance: Shimmer, count: Int = 5) {
  items(count) {
    ItemVerticalAnimeShimmer(
      modifier = ItemVerticalAnimeConfig.defaultModifier,
      shimmerInstance = shimmerInstance
    )
  }
}