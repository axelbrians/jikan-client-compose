package com.machina.jikan_client_compose.presentation.content_detail_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
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
import com.google.accompanist.insets.navigationBarsHeight
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.HorizontalContentHeaderConfig
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.*
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsNavArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsScreenNavigator
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

private object ContentDetailsScreenSection {
  const val ThreeColumn = "three_column_section"
  const val ContentDescription = "content_description_composable"
  const val ContentGenre = "content_genre_chips"
  const val ContentTrailer = "content_trailer"
  const val ContentCharacters = "content_characters"
  const val ContentSimilar = "content_similar"
  const val ContentPhotos = "content_photos"
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun ContentDetailsScreen(
  navigator: ContentDetailsScreenNavigator,
  viewModel: ContentDetailsViewModel,
  navArgs: ContentDetailsNavArgs
) {
  val animateDuration = 250
  val density = LocalDensity.current
  val screenWidth = LocalConfiguration.current.screenWidthDp.dp
  val contentHeight = with(density) {
    (190 * screenWidth.roundToPx() / 140).toDp()
  }
//  Timber.d("screenWidth: $screenWidth")
//  Timber.d("contentHeight: $contentHeight")

  val extraContentPadding = with(density) { 12.dp.toPx() }
  val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
  var isSynopsisExpanded by remember { mutableStateOf(false) }
  var isGalleryPageOpen by remember { mutableStateOf(false) }
  val contentDetailsState by viewModel.contentDetailsState
  val animeCharactersState by viewModel.animeCharactersState
  val animeRecommendationsState by viewModel.animeRecommendationsState
  val animePhotos by viewModel.animePicturesState

  var clickedImageUrl by remember { mutableStateOf("") }
  var yAxisOffset by remember { mutableStateOf(0.dp) }
  var xAxisOffset by remember { mutableStateOf(0.dp) }
  var xAxisStart by remember { mutableStateOf(0.dp) }
  var xAxisTarget by remember { mutableStateOf(0.dp) }


  val animatedYDp by animateDpAsState(
    targetValue = if (isGalleryPageOpen) 0.dp else yAxisOffset,
    animationSpec = tween(animateDuration)
  )
  var animatedXDp by remember { mutableStateOf(0.dp) }
  val animXDp = remember(isGalleryPageOpen) {
    TargetBasedAnimation(
      animationSpec = tween(animateDuration),
      typeConverter = Dp.VectorConverter,
      initialValue = xAxisStart,
      targetValue = xAxisTarget
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

  LaunchedEffect(animXDp) {
    val startTime = withFrameNanos { it }
    do {
      val playTime = withFrameNanos { it } - startTime
      animatedXDp = animXDp.getValueFromNanos(playTime)
    } while (animatedXDp != if (isGalleryPageOpen) xAxisOffset else 0.dp)
  }


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
} else {
    CollapsingToolbarScaffold(
      modifier = Modifier
        .fillMaxSize()
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
            state = contentDetailsState)
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
            modifier = Modifier,
            itemModifier = ItemVerticalAnimeModifier.Small,
            shimmer = rememberShimmerCustomBounds(),
            thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightSmall,
            headerTitle = Constant.CHARACTERS,
            contentState = animeCharactersState,
            contentPadding = PaddingValues(horizontal = 12.dp),
            contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default,
            textAlign = TextAlign.Center,
            onIconClick = {
              navigator.navigateToContentSmallViewAllScreen(
                Constant.CHARACTERS,
                Endpoints.getAnimeCharactersEndpoint(contentDetailsState.data?.malId ?: 0)
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
            contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default,
            onIconClick = {
              navigator.navigateToContentViewAllScreen(
                "${Constant.SIMILAR} to ${contentDetailsState.data?.title}",
                Endpoints.getAnimeRecommendationEndpoint(contentDetailsState.data?.malId ?: 0)
              )
            },
            onItemClick = navigator::navigateToContentDetailsScreen
          )
        }


        item(key = ContentDetailsScreenSection.ContentPhotos) {
          val lazyListState = rememberLazyListState()

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
            state = lazyListState,
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default,
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
                    clickedImageUrl = imageUrl

                    val xOffset = lazyListState.layoutInfo.visibleItemsInfo.find {
                      it.index == index
                    }?.offset ?: 0
                    xAxisOffset = with(density) {
                      (xOffset.toFloat() + extraContentPadding).toDp()
                    }
                    xAxisStart = xAxisOffset
                    xAxisTarget = 0.dp
                    animatedXDp = xAxisOffset
                    isGalleryPageOpen = !isGalleryPageOpen
                  },
                model = imageUrl,
                contentDescription = "Anime Photos",
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


    if (isGalleryPageOpen) {
      Box(
        modifier = Modifier
          .zIndex(5f)
          .fillMaxSize()
          .background(Color.Black.copy(alpha = 0.7f))
      ) {
        SubcomposeAsyncImage(
          model = clickedImageUrl,
          contentScale = ContentScale.Crop,
          contentDescription = "Zoomed image",
          loading = {
            CenterCircularProgressIndicator(
              strokeWidth = 2.dp,
              size = 20.dp,
              color = MyColor.Yellow500
            )
          },
          modifier = Modifier
            .graphicsLayer {
              with(density) {
                translationX = animatedXDp.toPx()
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
            ),
        )
        Box(
          modifier = Modifier
            .fillMaxSize()
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null,
              onClick = {
                isGalleryPageOpen = !isGalleryPageOpen
              }
            )
        )
      }
    }
  }
}