package com.machina.jikan_client_compose.presentation.home_screen.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.domain.use_case.anime.SectionType
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeaderDefaults
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.home_screen.HomeScreenNavigator
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.AnimeHeadlineCarousel
import com.machina.jikan_client_compose.presentation.home_screen.viewmodel.HomeViewModel

object ItemHomeSectionDefaults {

	@Composable
	fun Placeholder(modifier: Modifier = Modifier) {
		Column(modifier = modifier
			.fillMaxWidth()
			.padding(12.dp)) {
			HorizontalContentHeaderDefaults.Placeholder()
			Spacer(modifier = Modifier.height(12.dp))
			LazyRow(
				userScrollEnabled = false,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				items(6) {
					CardThumbnailPortraitDefault.Placeholder(
						modifier = Modifier.width(CardThumbnailPortraitDefault.Width.Small),
						thumbnailHeight = CardThumbnailPortraitDefault.Height.Small
					)
				}
			}
		}
	}
}

@Suppress("FunctionName")
fun LazyListScope.ItemHomeSection(
	state: HomeViewModel.HomeState,
	navigator: HomeScreenNavigator,
	modifierProvider: (Int) -> Modifier
) {
	if (state is HomeViewModel.HomeState.Success) {
		itemsIndexed(
			items = state.sections,
			key = { _, section -> section.id },
			contentType = { _, section -> section.type }
		) { index, section ->
			ItemHomeSectionInternal(
				section = section,
				navigator = navigator,
				modifier = modifierProvider(index)
			)
		}
	} else if (state is HomeViewModel.HomeState.Loading) {
		if (state.sections.isEmpty()) {
			items(3) {
				ItemHomeSectionDefaults.Placeholder()
			}
		} else {
			itemsIndexed(
				items = state.sections,
				key = { _, section -> section.id },
				contentType = { _, section -> section.type }
			) { index, section ->
				ItemHomeSectionInternal(
					section = section,
					navigator = navigator,
					modifier = modifierProvider(index)
				)
			}
		}
	}
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ItemHomeSectionInternal(
	section: HomeSection,
	navigator: HomeScreenNavigator,
	modifier: Modifier = Modifier
) {
	when (section.type) {
		is SectionType.AnimeAiringPopular -> {
			AnimeHeadlineCarousel(
				dataSet = section.contents,
				onClick = navigator::navigateToContentDetailsScreen,
				modifier = Modifier
					.fillMaxWidth()
					.then(modifier)
			)
		}

		is SectionType.AnimeSchedule -> {
			HorizontalContentHeader(
				title = Constant.AIRING_TODAY,
				onButtonClick = {
					navigator.navigateToContentViewAllScreen(
						title = Constant.AIRING_TODAY,
						url = Endpoints.getTodayScheduleAnimeEndpoints()
					)
				},
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 12.dp)
					.then(modifier)
			)

			ScrollableHorizontalContent(
				data = section,
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = CardThumbnailPortraitDefault.Arrangement.Default,
				thumbnailHeight = CardThumbnailPortraitDefault.Height.Small,
				onItemClick = navigator::navigateToContentDetailsScreen,
				modifier = modifier
			)
		}

		is SectionType.AnimeTop -> {
			HorizontalContentHeader(
				title = Constant.TOP_ANIME_OF_ALL_TIMES,
				onButtonClick = { },
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 12.dp)
					.then(modifier)
			)
			ScrollableHorizontalContent(
				data = section,
				contentPadding = PaddingValues(horizontal = 12.dp),
				contentArrangement = CardThumbnailPortraitDefault.Arrangement.Default,
				thumbnailHeight = CardThumbnailPortraitDefault.Height.Small,
				onItemClick = navigator::navigateToContentDetailsScreen,
				modifier = modifier
			)
		}
	}
}