package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.domain.use_case.anime.SectionType
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.AnimeHeadlineCarousel
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Arrangement
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@ExperimentalCoilApi
@Composable
fun HomeContentList(
	navigator: HomeScreenNavigator,
	homeSections: StateFlow<ImmutableList<HomeSection>>,
	modifier: Modifier = Modifier,
) {
	val homeSectionsState = homeSections.collectAsState()

	val pullToRefreshState = rememberPullToRefreshState()

	LaunchedEffect(pullToRefreshState.isRefreshing) {
		// fetch something
		if (pullToRefreshState.isRefreshing) {
			delay(5.seconds)
			pullToRefreshState.endRefresh()
		}
	}

	LazyColumn(modifier = modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
		item {
			PullToRefreshContainer(
				state = pullToRefreshState,
				modifier = Modifier
			)
		}
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
									title = Constant.AIRING_TODAY,
									url = Endpoints.getTodayScheduleAnimeEndpoints()
								)
							}
						},
						onItemClick = navigator::navigateToContentDetailsScreen,
					)
				}

				is SectionType.AnimeTop -> {
					ScrollableHorizontalContent(
						headerTitle = Constant.TOP_ANIME_OF_ALL_TIMES,
						data = section,contentPadding = PaddingValues(horizontal = 12.dp),
						contentArrangement = Arrangement.Default,
						thumbnailHeight = Height.Small,
						onIconClick = { },
						onItemClick = navigator::navigateToContentDetailsScreen
					)
				}
			}
		}
	}
}