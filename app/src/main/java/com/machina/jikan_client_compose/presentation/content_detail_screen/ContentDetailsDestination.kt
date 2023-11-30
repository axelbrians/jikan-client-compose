package com.machina.jikan_client_compose.presentation.content_detail_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.navigationBarsHeight
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.navigation.Destination
import com.machina.jikan_client_compose.navigation.destinationParam
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeaderConfig
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsScreenToolbar
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsSynopsis
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsThreeColumnSection
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.GenreChip
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

internal object ContentDetailsScreenSection {
	const val ThreeColumn = "three_column_section"
	const val ContentDescription = "content_description_composable"
	const val ContentGenre = "content_genre_chips"
	const val ContentTrailer = "content_trailer"
	const val ContentCharacters = "content_characters"
	const val ContentSimilar = "content_similar"
	const val ContentPhotos = "content_photos"
}

object ContentDetailsDestination: Destination(
	destinationParam {
		route = "home/content/details"
		requiredNav(
			navArgument(ContentDetailsDestination.KEY_CONTENT_DETAIL_ARGS) {
				type = ContentDetailsArgs
			}
		)
		requiredNav(
			navArgument(ContentDetailsDestination.KEY_MAGIC_NUMBER) {
				type = NavType.IntType
			}
		)
	}
) {
	const val KEY_CONTENT_DETAIL_ARGS = "contentDetailArgs"
	const val KEY_MAGIC_NUMBER = "magicNumber"

	fun createRoute(args: ContentDetailsArgs, number: Int): String {
		return super.createDestinationRoute(
			KEY_CONTENT_DETAIL_ARGS to args,
			KEY_MAGIC_NUMBER to number
		)
	}

}

@Composable
@OptIn(ExperimentalAnimationApi::class, ExperimentalCoilApi::class)
fun ContentDetailsScreen(
	navigator: ContentDetailsScreenNavigator,
	viewModel: ContentDetailsViewModel,
	navArgs: ContentDetailsArgs,
	modifier: Modifier = Modifier
) {
	val galleryListState = rememberLazyListState()

	val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
	var isSynopsisExpanded by remember { mutableStateOf(false) }
	val contentDetailsState by viewModel.contentDetailsState
	val animeCharactersState by viewModel.animeCharactersState
	val animeRecommendationsState by viewModel.animeRecommendationsState
	val animePhotos by viewModel.animePicturesState


	LaunchedEffect(navArgs.malId) {
		viewModel.getContentDetails(navArgs.contentType.name, navArgs.malId)
		viewModel.getAnimeCharacters(navArgs.malId)
		viewModel.getAnimeRecommendations(navArgs.malId)
		viewModel.getAnimePictures(navArgs.malId)
	}

	if (contentDetailsState.isLoading) {
		CenterCircularProgressIndicator(
			size = 40.dp,
			color = MyColor.Yellow500
		)

		return
	}

	CollapsingToolbarScaffold(
		modifier = modifier
			.background(MyColor.DarkBlueBackground),
		state = toolbarScaffoldState,
		scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
		toolbar = {
			ContentDetailsScreenToolbar(
				contentDetailsState = contentDetailsState,
				toolbarScaffoldState = toolbarScaffoldState,
				onArrowClick = navigator::navigateUp
			)
		}
	) {
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth(),
			contentPadding = PaddingValues(bottom = 32.dp),
			horizontalAlignment = Alignment.Start,
		) {
			// Three Column Section
			item(key = ContentDetailsScreenSection.ThreeColumn) {
				ContentDetailsThreeColumnSection(
					modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
					state = contentDetailsState
				)
			}


			// Synopsis Composable
			item(key = ContentDetailsScreenSection.ContentDescription) {
				ContentDetailsSynopsis(
					state = contentDetailsState,
					isExpanded = isSynopsisExpanded,
					onExpandChanged = { isSynopsisExpanded = it }
				)
			}


			// Genre FlowRow Chips
			item(key = ContentDetailsScreenSection.ContentGenre) {
				val genres = contentDetailsState.data?.genres.orEmpty()
				if (genres.isNotEmpty()) {
					if (isSynopsisExpanded) {
						FlowRow(
							modifier = Modifier.padding(horizontal = 10.dp),
							lastLineMainAxisAlignment = FlowMainAxisAlignment.Start,
							mainAxisSpacing = 4.dp,
							crossAxisSpacing = 4.dp
						) {
							genres.forEach {
								GenreChip(text = it.name)
							}
						}
					} else {
						LazyRow(
							contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
							horizontalArrangement = Arrangement.spacedBy(4.dp)
						) {
							this.items(genres) { genre ->
								GenreChip(text = genre.name)
							}
						}
					}
				}
			}

			// Content Trailer (if any, like TV or Movies or Anime)
//			if (contentDetailsState.data?.trailer?.embedUrl?.isNotEmpty() == true) {
//				item(key = ContentDetailsScreenSection.ContentTrailer) {
//					ContentDetailsTrailerPlayer(
//						modifier = Modifier
//							.padding(top = 12.dp)
//							.height(240.dp)
//							.fillMaxWidth(),
//						trailerUrl = contentDetailsState.data?.trailer?.embedUrl!!
//					)
//				}
//			}

			// Anime Characters List
			item(key = ContentDetailsScreenSection.ContentCharacters) {
				ScrollableHorizontalContent(
					itemModifier = Modifier.width(CardThumbnailPortraitDefault.Width.Small),
					shimmer = rememberShimmerCustomBounds(),
					thumbnailHeight = CardThumbnailPortraitDefault.Height.Small,
					headerTitle = Constant.CHARACTERS,
					contentState = animeCharactersState,
					contentPadding = PaddingValues(horizontal = 12.dp),
					contentArrangement = CardThumbnailPortraitDefault.Arrangement.Default,
					textAlign = TextAlign.Center,
					onHeaderClick = {
						navigator.navigateToContentSmallViewAllScreen(
							Constant.CHARACTERS,
							Endpoints.getAnimeCharactersEndpoint(
								contentDetailsState.data?.malId ?: 0
							)
						)
					},
					onItemClick = { _, _ -> }
				)
			}

			// Similar Content with the one currently displayed
			item(key = ContentDetailsScreenSection.ContentSimilar) {
				ScrollableHorizontalContent(
					modifier = Modifier,
					shimmer = rememberShimmerCustomBounds(),
					headerTitle = Constant.SIMILAR,
					contentState = animeRecommendationsState,
					contentPadding = PaddingValues(horizontal = 12.dp),
					contentArrangement = CardThumbnailPortraitDefault.Arrangement.Default,
					onHeaderClick = {
						navigator.navigateToContentViewAllScreen(
							"${Constant.SIMILAR} to ${contentDetailsState.data?.title}",
							Endpoints.getAnimeRecommendationEndpoint(
								contentDetailsState.data?.malId ?: 0
							)
						)
					},
					onItemClick = navigator::navigateToContentDetailsScreen
				)
			}


			item(key = ContentDetailsScreenSection.ContentPhotos) {

				if (animePhotos.isLoading) {
					ContentListHeaderWithButtonShimmer(
						shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View),
						showButton = false
					)
				} else {
					HorizontalContentHeader(
						modifier = HorizontalContentHeaderConfig.Default,
						title = "Gallery photos"
					)
				}

				LazyRow(
					state = galleryListState,
					contentPadding = PaddingValues(horizontal = 12.dp),
					horizontalArrangement = CardThumbnailPortraitDefault.Arrangement.Default
				) {
					itemsIndexed(animePhotos.data) { index, imageUrl ->
						SubcomposeAsyncImage(
							modifier = Modifier
								.width(140.dp)
								.height(190.dp)
								.clip(MyShape.Rounded6),
							model = imageUrl,
							contentDescription = "Character Portrait",
							contentScale = ContentScale.Crop,
							loading = {
								CenterCircularProgressIndicator(
									strokeWidth = 2.dp,
									size = 20.dp,
									color = MyColor.Yellow500
								)
							}
						)
					}
				}

				Spacer(modifier = Modifier.navigationBarsHeight(additional = 12.dp))
			}
		}
	}

}
