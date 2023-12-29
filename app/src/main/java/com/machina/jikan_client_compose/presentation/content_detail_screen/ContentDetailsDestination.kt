package com.machina.jikan_client_compose.presentation.content_detail_screen

import android.view.Window
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.navigation.Destination
import com.machina.jikan_client_compose.navigation.composable
import com.machina.jikan_client_compose.navigation.destinationParam
import com.machina.jikan_client_compose.presentation.composable.CenterCircularLoading
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeaderDefaults
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsHeader
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
import timber.log.Timber

internal object ContentDetailsScreenSection {
	const val Header = "header"
	const val ThreeColumn = "threeColumn"
	const val ContentDescription = "contentDescription"
	const val ContentGenre = "contentGenreChips"
	const val ContentTrailer = "contentTrailer"
	const val ContentCharacters = "contentCharacters"
	const val ContentSimilar = "contentSimilar"
	const val ContentPhotos = "contentPhotos"
}

object ContentDetailsDestination: Destination(
	destinationParam = destinationParam {
		// todo: ab
		// DSL karena route wajib ada, harus jadi required
		// Define route nya kalo bisa ngikutin KTOR, pake path based. bisa apped static path atau
		// dynamic (dynamic ini bakal di replace sama argument) static adalah route aslinya.
		// route = "home/content/{}/details"
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
//		optionalNav(
//			navArgument("optional") {
//				type = NavType.IntType
//			}
//		)
	}
) {
	const val KEY_CONTENT_DETAIL_ARGS = "contentDetailArgs"
	const val KEY_MAGIC_NUMBER = "magicNumber"

	fun constructRoute(args: ContentDetailsArgs, number: Int = 0): String {
		return super.createDestinationRoute(
			KEY_CONTENT_DETAIL_ARGS to args,
			KEY_MAGIC_NUMBER to number
		)
	}
}

fun NavGraphBuilder.addContentDetailsScreen(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController
) {
	composable(ContentDetailsDestination) { backStack ->
		OnDestinationChanged(
			systemUiController = systemUiController,
			drawOverStatusBar = false,
			window = window,
		)

		// todo: ab
		// Pindahin parsing argument ke Custom Scope
//			this.navArgs
//			this.magicNumber
		val navArgs = ContentDetailsArgs.requireGet(
			bundle = backStack.arguments,
			key = ContentDetailsDestination.KEY_CONTENT_DETAIL_ARGS
		)
		val magicNumber = backStack.arguments?.getInt(ContentDetailsDestination.KEY_MAGIC_NUMBER)

		Timber.tag("puyo").d("args: $navArgs")
		Timber.tag("puyo").d("number: $magicNumber")

		ContentDetailsScreen(
			navigator = ContentDetailsScreenNavigator(navController),
			viewModel = hiltViewModel(),
			navArgs = navArgs,
			modifier = Modifier.fillMaxSize()
		)
	}
}

@Composable
@OptIn(ExperimentalAnimationApi::class, ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
fun ContentDetailsScreen(
	navigator: ContentDetailsScreenNavigator,
	viewModel: ContentDetailsViewModel,
	navArgs: ContentDetailsArgs,
	modifier: Modifier = Modifier
) {
	val galleryListState = rememberLazyListState()

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
		CenterCircularLoading(
			size = 40.dp,
			color = MyColor.Yellow500
		)

		return
	}

	Scaffold(
		modifier = modifier,
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = contentDetailsState.data?.title ?: "-",
						overflow = TextOverflow.Ellipsis,
						maxLines = 1,
						style = TextStyle(
							fontWeight = FontWeight.Bold,
							fontSize = 20.sp
						)
					)
				},
				navigationIcon = {
					IconButton(onClick = navigator::navigateUp) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = "back icon"
						)
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = MaterialTheme.colorScheme.surface,
					navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
					titleContentColor = MaterialTheme.colorScheme.onSurface
				)
			)
		}
	) {
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth()
				.padding(it),
			contentPadding = PaddingValues(bottom = 32.dp),
			horizontalAlignment = Alignment.Start,
		) {
			item(key = ContentDetailsScreenSection.Header) {
				ContentDetailsHeader(contentDetailsState = contentDetailsState)
			}

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
					onExpandChanged = { isExpanded -> isSynopsisExpanded = isExpanded }
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
						modifier = HorizontalContentHeaderDefaults.Default,
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
								CenterCircularLoading(
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
