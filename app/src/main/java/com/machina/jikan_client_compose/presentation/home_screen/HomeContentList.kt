package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.AnimeHeadlineCarousel
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import kotlinx.coroutines.flow.StateFlow

object HomeContentList {

}

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun HomeContentList(
	modifier: Modifier = Modifier,
	navigator: HomeScreenNavigator,
	airingPopular: StateFlow<List<AnimeThumbnail>>,
	animeScheduleState: State<StateListWrapper<AnimePortraitDataModel>>,
	animeTopState: State<StateListWrapper<AnimePortraitDataModel>>
) {
	val lazyColumnState = rememberLazyListState()
	val airingPopularState = airingPopular.collectAsState()

	LazyColumn(
		modifier = modifier,
		state = lazyColumnState
	) {

		/* - - - Start of Currently popular anime - - - */
		item(key = "airing_popular") {
			AnimeHeadlineCarousel(
				dataSet = airingPopularState.value,
				onClick = navigator::navigateToContentDetailsScreen
			)
		}
		/* End of Currently Popular Anime */


		/* Start of Anime Airing Today */
		item(key = "anime_schedule_list") {
			ScrollableHorizontalContent(
				modifier = Modifier,
				shimmer = rememberShimmerCustomBounds(),
				headerTitle = Constant.AIRING_TODAY,
				contentState = animeScheduleState.value,
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = CardThumbnailPortraitDefault.Arrangement.Default,
				onIconClick = {
					navigator.navigateToContentViewAllScreen(
						Constant.AIRING_TODAY,
						Endpoints.getTodayScheduleAnimeEndpoints()
					)
				},
				onItemClick = navigator::navigateToContentDetailsScreen
			)
		}
		// End of Anime Airing Today


		// Start of Top Anime of All Times
		item(key = "anime_top_list") {ScrollableHorizontalContent(
			modifier = Modifier,
			shimmer = rememberShimmerCustomBounds(),
			headerTitle = Constant.TOP_ANIME_OF_ALL_TIMES,
			contentState = animeTopState.value,
			contentPadding = PaddingValues(horizontal = 12.dp),
			contentArrangement = CardThumbnailPortraitDefault.Arrangement.Default,
			onIconClick = {
				navigator.navigateToContentViewAllScreen(
					Constant.TOP_ANIME_OF_ALL_TIMES,
					Endpoints.ANIME_TOP
				)
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