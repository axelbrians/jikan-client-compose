package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.domain.use_case.anime.SectionType
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.AnimeHeadlineCarousel
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Arrangement
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
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
	animeTopState: State<StateListWrapper<AnimePortraitDataModel>>,
	homeSections: StateFlow<List<HomeSection>>
) {
	val lazyColumnState = rememberLazyListState()
	val homeSectionsState = homeSections.collectAsState()

	LazyColumn(
		modifier = modifier,
		state = lazyColumnState
	) {
		items(
			items = homeSectionsState.value,
			key = { it.id },
			contentType = { it.type }
		) { section ->
			when (section.type) {
				is SectionType.AnimeAiringPopular -> {
					AnimeHeadlineCarousel(
						dataSet = section.contents,
						onClick = navigator::navigateToContentDetailsScreen
					)
				}

				is SectionType.AnimeSchedule -> {
					ScrollableHorizontalContent(
						data = section,
						headerTitle = Constant.AIRING_TODAY,
						contentPadding = PaddingValues(horizontal = 12.dp),
						contentArrangement = Arrangement.Default,
						thumbnailHeight = Height.Small,
						onIconClick = remember {
							{
								navigator.navigateToContentViewAllScreen(
									Constant.AIRING_TODAY,
									Endpoints.getTodayScheduleAnimeEndpoints()
								)
							}
						},
						onItemClick = navigator::navigateToContentDetailsScreen,
					)
				}

				else -> {}
			}
		}

		/* - - - Start of Currently popular anime - - - */
//		item(key = "airing_popular") {
//			AnimeHeadlineCarousel(
//				dataSet = airingPopularState.value,
//				onClick = navigator::navigateToContentDetailsScreen
//			)
//		}
		/* End of Currently Popular Anime */


		/* Start of Anime Airing Today */
//		item(key = "anime_schedule_list") {
//			ScrollableHorizontalContent(
//				modifier = Modifier,
//				shimmer = rememberShimmerCustomBounds(),
//				headerTitle = Constant.AIRING_TODAY,
//				contentState = animeScheduleState.value,
//				contentPadding = PaddingValues(horizontal = 12.dp),
//				contentArrangement = CardThumbnailPortraitDefault.Arrangement.Default,
//				thumbnailHeight = CardThumbnailPortraitDefault.Height
//					.Small,
//				onIconClick = {
//					navigator.navigateToContentViewAllScreen(
//						Constant.AIRING_TODAY,
//						Endpoints.getTodayScheduleAnimeEndpoints()
//					)
//				},
//				onItemClick = navigator::navigateToContentDetailsScreen
//			)
//		}
		// End of Anime Airing Today


		// Start of Top Anime of All Times
		item(key = "anime_top_list") {
			ScrollableHorizontalContent(
				modifier = Modifier,
				shimmer = rememberShimmerCustomBounds(),
				headerTitle = Constant.TOP_ANIME_OF_ALL_TIMES,
				contentState = animeTopState.value,
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = Arrangement.Default,
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