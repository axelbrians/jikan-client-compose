package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.AnimeAiringPopularHorizontalPager
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
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
  animeAiringPopularState: StateListWrapper<AnimeAiringPopular>,
  animeScheduleState: StateListWrapper<AnimeVerticalDataModel>,
  animeTopState: StateListWrapper<AnimeVerticalDataModel>
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
        shimmerInstance = shimmerInstance,
        navigateToContentDetailsScreen = navigator::navigateToContentDetailsScreen
      )
    }
    /* End of Currently Popular Anime */


    /* Start of Anime Airing Today */
    item(key = "anime_schedule_list") {
      val title = "Airing today"

      ScrollableHorizontalContent(
        modifier = Modifier,
        shimmer = rememberShimmerCustomBounds(),
        headerTitle = title,
        contentState = animeScheduleState,
        contentPadding = PaddingValues(horizontal = 12.dp),
        contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default,
        onIconClick = {
          navigator.navigateToContentViewAllScreen(
            title,
            Endpoints.getTodayScheduleAnimeEndpoints()
          )
        },
        onItemClick = navigator::navigateToContentDetailsScreen
      )

//
//      if (animeScheduleState.isLoading) {
//        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmerInstance)
//      } else {
//        HorizontalContentHeader(
//          modifier = HorizontalContentHeaderConfig.default,
//          title = title,
//          onButtonClick = direction
//        )
//      }
//
//      LazyRow(
//        contentPadding = PaddingValues(horizontal = 12.dp),
//        modifier = Modifier.onUpdateShimmerBounds(shimmerInstance),
//        horizontalArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default
//      ) {
//        if (animeScheduleState.isLoading) {
//          showItemVerticalAnimeShimmer(shimmerInstance)
//        } else {
//          items(animeScheduleState.data.data, key = { it.malId }) { data ->
//            ItemVerticalAnime(
//              modifier = ItemVerticalAnimeModifier.default,
//              data = data,
//              onClick = navigator::navigateToContentDetailsScreen
//            )
//          }
//          showItemVerticalAnimeMoreWhenPastLimit(
//            modifier = ItemVerticalAnimeModifier.default,
//            thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightDefault,
//            size = animeScheduleState.data.data.size,
//            onClick = direction
//          )
//        }
//      }
    }
    // End of Anime Airing Today


    // Start of Top Anime of All Times
    item(key = "anime_top_list") {
      val title = "Top anime of all times"

      ScrollableHorizontalContent(
        modifier = Modifier,
        shimmer = rememberShimmerCustomBounds(),
        headerTitle = title,
        contentState = animeTopState,
        contentPadding = PaddingValues(horizontal = 12.dp),
        contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default,
        onIconClick = {
          navigator.navigateToContentViewAllScreen(title, Endpoints.ANIME_TOP)
        },
        onItemClick = navigator::navigateToContentDetailsScreen
      )

//      if (animeTopState.isLoading) {
//        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmerInstance)
//      } else {
//        HorizontalContentHeader(
//          modifier = HorizontalContentHeaderConfig.default,
//          title = title,
//          onButtonClick = direction
//        )
//      }
//
//      LazyRow(
//        contentPadding = PaddingValues(horizontal = 12.dp),
//        modifier = Modifier.onUpdateShimmerBounds(shimmerInstance),
//        horizontalArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default
//      ) {
//        if (animeTopState.isLoading) {
//          showItemVerticalAnimeShimmer(shimmerInstance)
//        } else {
//          items(animeTopState.data.data, key = { item -> item.malId }) { model ->
//            ItemVerticalAnime(
//              modifier = ItemVerticalAnimeModifier.default,
//              data = model,
//              onClick = navigator::navigateToContentDetailsScreen
//            )
//          }
//          showItemVerticalAnimeMoreWhenPastLimit(
//            modifier = ItemVerticalAnimeModifier.default,
//            thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightDefault,
//            size = animeTopState.data.data.size,
//            onClick = direction
//          )
//        }
//      }
    }
    // End of Top Anime of All Times


  }
}