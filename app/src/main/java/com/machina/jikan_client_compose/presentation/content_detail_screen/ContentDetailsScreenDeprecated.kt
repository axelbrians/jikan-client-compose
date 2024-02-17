package com.machina.jikan_client_compose.presentation.content_detail_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.presentation.composable.CenterCircularLoading
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeaderDefaults
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsHeader
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsSynopsis
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsThreeColumnSection
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsTrailerPlayer
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.GenreChip
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.launch

@Deprecated(message = "Use ContentDetailsScreen instead")
@OptIn(ExperimentalPagerApi::class)
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun ContentDetailsScreenDeprecated(
	navigator: ContentDetailsScreenNavigator,
	viewModel: ContentDetailsViewModel,
	navArgs: ContentDetailsNavigation.ContentDetailsArgs
) {
	val animateDuration = 250
	val density = LocalDensity.current
	val statusBarHeight = with(density) { LocalWindowInsets.current.statusBars.top.toDp() }
	val screenWidth = LocalConfiguration.current.screenWidthDp.dp
	val contentHeight = with(density) {
		(190 * screenWidth.roundToPx() / 140).toDp()
	}

	val coroutineScope = rememberCoroutineScope()
	val galleryListState = rememberLazyListState()
	val overlayListState = rememberLazyListState()
	val pagerState = rememberPagerState()

	val extraContentPadding = 24.dp - 140.dp
	var isSynopsisExpanded by remember { mutableStateOf(false) }
	val contentDetailsState by viewModel.contentDetailsState
	val animeCharactersState by viewModel.animeCharactersState
	val animeRecommendationsState by viewModel.animeRecommendationsState
	val animePhotos by viewModel.animePicturesState


	var isGalleryPageOpen by remember { mutableStateOf(false) }
	var isOverlayVisible by remember { mutableStateOf(false) }
	var selectedImageUrl by remember { mutableStateOf("") }
	var selectedImageIndex by remember { mutableStateOf(0) }
	var yAxisOffset by remember { mutableStateOf(0.dp) }
	var xAxisOffset by remember { mutableStateOf(0.dp) }
	var xAxisStart by remember { mutableStateOf(0.dp) }
	var xAxisTarget by remember { mutableStateOf(0.dp) }
	var xAxisOffsetBlocker by remember { mutableStateOf(0.dp) }
	var xAxisStartBlocker by remember { mutableStateOf(0.dp) }
	var xAxisTargetBlocker by remember { mutableStateOf(0.dp) }

	val animatedYDp by animateDpAsState(
		targetValue = if (isGalleryPageOpen) statusBarHeight else yAxisOffset,
		animationSpec = tween(animateDuration)
	)
	var animatedXDp by remember { mutableStateOf(0.dp) }
	var animatedXDpBlocker by remember { mutableStateOf(0.dp) }
	val animXDp = remember(isGalleryPageOpen) {
		TargetBasedAnimation(
			animationSpec = tween(animateDuration),
			typeConverter = Dp.VectorConverter,
			initialValue = xAxisStart,
			targetValue = xAxisTarget
		)
	}
	val animXDpBlocker = remember(isGalleryPageOpen) {
		TargetBasedAnimation(
			animationSpec = tween(animateDuration),
			typeConverter = Dp.VectorConverter,
			initialValue = xAxisStartBlocker,
			targetValue = xAxisTargetBlocker
		)
	}
	val animatedContentWidth by animateDpAsState(
		targetValue = if (isGalleryPageOpen) screenWidth else 140.dp,
		animationSpec = tween(animateDuration)
	)
	val animatedContentHeight by animateDpAsState(
		targetValue = if (isGalleryPageOpen) contentHeight else 190.dp,
		animationSpec = tween(animateDuration)
	)
	val animatedBgColor by animateColorAsState(
		targetValue = if (isGalleryPageOpen) Color.Black.copy(alpha = 0.7f) else Color.Transparent,
		animationSpec = tween(animateDuration)
	)

	val closeOverlayAction = {
		selectedImageUrl = if (pagerState.currentPage in 0 until animePhotos.data.size) {
			animePhotos.data[pagerState.currentPage]
		} else {
			""
		}
		val index = pagerState.currentPage
		val isLastSelectedVisible = galleryListState.layoutInfo.visibleItemsInfo.find {
			it.index == index
		} == null

		coroutineScope.launch {
			if (isLastSelectedVisible) {
				galleryListState.scrollToItem(index)
			}
			val xOffset = galleryListState.layoutInfo.visibleItemsInfo.find {
				it.index == index
			}?.offset ?: 0
			xAxisOffset = with(density) {
				xOffset.toFloat().toDp() + extraContentPadding
			}
			xAxisStart = 0.dp
			xAxisTarget = xAxisOffset
			animatedXDp = 0.dp

			xAxisOffsetBlocker = xAxisOffset + 140.dp - 12.dp
			xAxisStartBlocker = 0.dp
			xAxisTargetBlocker = xAxisOffsetBlocker
			animatedXDpBlocker = 0.dp
			isGalleryPageOpen = false
		}
		Unit
	}

	LaunchedEffect(pagerState.currentPage) {
		overlayListState.animateScrollToItem(pagerState.currentPage)
	}

	LaunchedEffect(key1 = animXDp, key2 = animXDpBlocker) {
		val targetValue = if (isGalleryPageOpen) 0.dp else xAxisOffset
		val startTime = withFrameNanos { it }
		do {
			val playTime = withFrameNanos { it } - startTime
			animatedXDp = animXDp.getValueFromNanos(playTime)
			animatedXDpBlocker = animXDpBlocker.getValueFromNanos(playTime)
		} while (animatedXDp != targetValue)
		if (targetValue == xAxisOffset) {
			isOverlayVisible = false
		}
	}

	LaunchedEffect(navArgs.malId) {
		viewModel.getContentDetails(navArgs.contentType.name, navArgs.malId)
		viewModel.getAnimeCharacters(navArgs.malId)
		viewModel.getAnimeRecommendations(navArgs.malId)
		viewModel.getAnimePictures(navArgs.malId)
	}

	BackHandler(enabled = isGalleryPageOpen) {
		closeOverlayAction()
	}

	if (contentDetailsState.isLoading) {
		CenterCircularLoading(
			size = 40.dp,
			color = MyColor.Yellow500
		)

		return
	}

	Scaffold(
		modifier = Modifier
			.fillMaxSize()
			.background(MyColor.DarkBlueBackground),
		topBar = {
			ContentDetailsHeader(
				contentDetailsState = contentDetailsState,
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
			if (contentDetailsState.data?.trailer?.embedUrl?.isNotEmpty() == true) {
				item(key = ContentDetailsScreenSection.ContentTrailer) {
					ContentDetailsTrailerPlayer(
						modifier = Modifier
							.padding(top = 12.dp)
							.height(240.dp)
							.fillMaxWidth(),
						trailerUrl = contentDetailsState.data?.trailer?.embedUrl!!
					)
				}
			}

			// Anime Characters List
			item(key = ContentDetailsScreenSection.ContentCharacters) {
				ScrollableHorizontalContent(
					itemModifier = Modifier.width(CardThumbnailPortraitDefault.Width.Small),
					shimmer = rememberShimmerCustomBounds(),
					thumbnailHeight = Height.Small,
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
					horizontalArrangement = CardThumbnailPortraitDefault.Arrangement.Default,
					modifier = Modifier.onGloballyPositioned {
						with(density) {
							yAxisOffset = it.positionInRoot().y.toDp()
						}
					}
				) {
					itemsIndexed(animePhotos.data) { index, imageUrl ->
						SubcomposeAsyncImage(
							modifier = Modifier
								.width(140.dp)
								.height(190.dp)
								.clip(MyShape.Rounded6)
								.clickable {
									selectedImageIndex = index
									selectedImageUrl = imageUrl

									val xOffset =
										galleryListState.layoutInfo.visibleItemsInfo.find {
											it.index == index
										}?.offset ?: 0
									xAxisOffset = with(density) {
										(xOffset.toFloat()).toDp() + extraContentPadding
									}
									xAxisStart = xAxisOffset
									xAxisTarget = 0.dp
									animatedXDp = xAxisOffset

									xAxisOffsetBlocker = xAxisOffset + 140.dp - 12.dp
									xAxisStartBlocker = xAxisOffsetBlocker
									xAxisTargetBlocker = 0.dp
									animatedXDpBlocker = xAxisOffsetBlocker

									isOverlayVisible = true
									isGalleryPageOpen = true
								},
							model = imageUrl,
							contentDescription = "Anime Photos",
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


	if (isOverlayVisible) {
		LaunchedEffect(Unit) {
			pagerState.scrollToPage(selectedImageIndex)
			overlayListState.animateScrollToItem(selectedImageIndex)
		}
		Box(
			modifier = Modifier
				.zIndex(5f)
				.fillMaxSize()
				.background(animatedBgColor)
		) {
			HorizontalPager(
				modifier = Modifier
					.fillMaxSize()
					.clickable(
						interactionSource = remember { MutableInteractionSource() },
						indication = null,
						onClick = closeOverlayAction
					),
				verticalAlignment = Alignment.Top,
				count = animePhotos.data.size,
				state = pagerState,
			) { index: Int ->
				val pagerItemModifier = if (pagerState.currentPage == index) {
					Modifier
						.graphicsLayer {
							with(density) {
								translationX = (animatedXDp).toPx()
								translationY = animatedYDp.toPx()
							}
						}
						.width(animatedContentWidth)
						.height(animatedContentHeight)
						.clip(MyShape.Rounded6)
						.clickable(
							enabled = true,
							interactionSource = remember { MutableInteractionSource() },
							indication = null,
							onClick = { }
						)
				} else {
					Modifier
						.statusBarsPadding()
						.fillMaxWidth()
						.height(contentHeight)
						.clickable(
							enabled = true,
							interactionSource = remember { MutableInteractionSource() },
							indication = null,
							onClick = { }
						)
				}
				SubcomposeAsyncImage(
					model = animePhotos.data[index],
					contentScale = ContentScale.Crop,
					contentDescription = "Zoomed image",
					loading = {
						CenterCircularLoading(
							strokeWidth = 2.dp,
							size = 20.dp,
							color = MyColor.Yellow500
						)
					},
					modifier = pagerItemModifier
				)
			}

			if (animatedYDp > statusBarHeight) {
				SubcomposeAsyncImage(
					model = selectedImageUrl,
					contentScale = ContentScale.Crop,
					contentDescription = "Zoomed image",
					loading = {
						CenterCircularLoading(
							strokeWidth = 2.dp,
							size = 20.dp,
							color = MyColor.Yellow500
						)
					},
					modifier = Modifier
						.graphicsLayer {
							with(density) {
								translationX = (animatedXDpBlocker).toPx()
								translationY = animatedYDp.toPx()
							}
						}
						.width(animatedContentWidth)
						.height(animatedContentHeight)
						.clip(MyShape.Rounded6)
				)
			}

			val smallImageModifier = Modifier
				.width(70.dp)
				.height(95.dp)
				.clip(MyShape.Rounded6)

			LazyRow(
				state = overlayListState,
				contentPadding = PaddingValues(horizontal = 12.dp),
				horizontalArrangement = Arrangement.spacedBy(4.dp),
				modifier = Modifier
					.fillMaxWidth()
					.align(Alignment.BottomCenter)
					.navigationBarsPadding()
			) {
				itemsIndexed(animePhotos.data) { index, imageUrl ->
					Box(
						modifier = (
						  if (pagerState.currentPage == index) {
							  smallImageModifier.border(
								  width = 2.dp,
								  color = MyColor.OnDarkSurfaceLight,
								  shape = MyShape.Rounded6
							  )
						  } else {
							  smallImageModifier
						  }
						  ).clickable {
								selectedImageUrl = imageUrl
								if (pagerState.currentPage != index) {
									coroutineScope.launch {
										pagerState.animateScrollToPage(index)
									}
								}
							}
					) {
						SubcomposeAsyncImage(
							modifier = Modifier.fillMaxSize(),
							model = imageUrl,
							contentDescription = "Anime Photos",
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
			}
		}
	}
}
