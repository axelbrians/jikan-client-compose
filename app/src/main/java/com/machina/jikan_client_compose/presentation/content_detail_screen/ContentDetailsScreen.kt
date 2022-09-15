package com.machina.jikan_client_compose.presentation.content_detail_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.data.remote.dto.common.Jpg.Companion.getHighestResImgUrl
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.*
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.presentation.content_detail_screen.item.ItemAnimeCharacterConfig
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsNavArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsScreenNavigator
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

private object Component {
  const val ThreeColumn = "three_column_section"
  const val ContentDescription = "content_description_composable"
  const val ContentGenre = "content_genre_chips"
  const val ContentTrailer = "content_trailer"
  const val ContentCharacters = "content_characters"
  const val ContentSimilar = "content_similar"
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun ContentDetailsScreen(
  navigator: ContentDetailsScreenNavigator,
  viewModel: ContentDetailsViewModel,
  navArgs: ContentDetailsNavArgs
) {
  var isSynopsisExpanded by remember { mutableStateOf(false) }
  val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
  val contentDetailsState by viewModel.contentDetailsState
  val animeCharacterListState by viewModel.animeCharactersListState
  val animeRecommendationsListState by viewModel.animeRecommendationsListState

  val largeImageCoil = rememberImagePainter(
    data = contentDetailsState.data?.images?.jpg?.getHighestResImgUrl(),
    builder = { crossfade(true) }
  )

  val smallImageCoil = rememberImagePainter(
    data = contentDetailsState.data?.images?.jpg?.getHighestResImgUrl(),
    builder = { crossfade(true) }
  )

  LaunchedEffect(
    key1 = navArgs.malId,
    block = {
      viewModel.getContentDetails(navArgs.contentType.name, navArgs.malId)
      viewModel.getAnimeCharacters(navArgs.malId)
      viewModel.getAnimeRecommendations(navArgs.malId)
    }
  )

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
          largeCoil = largeImageCoil,
          smallCoil = smallImageCoil,
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
        item(key = Component.ThreeColumn) {
          ContentDetailsThreeColumnSection(
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
            state = contentDetailsState)
        }


        // Synopsis Composable
        item(key = Component.ContentDescription) {
          ContentDetailsSynopsis(
            state = contentDetailsState,
            isExpanded = isSynopsisExpanded,
            onExpandChanged = { isSynopsisExpanded = it }
          )
        }


        // Genre FlowRow Chips
        item(key = Component.ContentGenre) {
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
          item(key = Component.ContentTrailer) {
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
        item(key = Component.ContentCharacters) {
          val shimmerInstance = rememberShimmerCustomBounds()
          val action =

          ScrollableHorizontalContent(
            modifier = Modifier,
            itemModifier = ItemAnimeCharacterConfig.default,
            shimmer = shimmerInstance,
            thumbnailHeight = ItemAnimeCharacterConfig.ThumbnailHeightFour,
            headerTitle = Constant.CHARACTERS,
            contentState = animeCharacterListState,
            contentPadding = PaddingValues(horizontal = 12.dp),
            contentArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default,
            onIconClick = {
              navigator.navigateToContentSmallViewAllScreen(
                Constant.CHARACTERS,
                Endpoints.getAnimeCharactersEndpoint(contentDetailsState.data?.malId ?: 0)
              )
            },
            onItemClick = { _, _ -> }
          )

//          HorizontalContentHeader(
//            modifier = Modifier
//              .fillMaxWidth()
//              .padding(start = 18.dp, end = 12.dp, top = 8.dp, bottom = 4.dp),
//            title = Constant.CHARACTERS,
//            onButtonClick = action
//          )
//
//          LazyRow(
//            modifier = Modifier.onUpdateShimmerBounds(shimmerInstance),
//            contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
//            horizontalArrangement = ItemVerticalAnimeModifier.HorizontalArrangement.Default
//          ) {
//            if (animeCharacterListState.isLoading) {
//              showItemVerticalAnimeShimmer(
//                shimmerInstance = shimmerInstance,
//                thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightSmall
//              )
//            } else {
//              items(
//                items = animeCharacterListState.data.take(Constant.HORIZONTAL_CHARACTERS_LIMIT),
//                key = { it.malId }
//              ) {
//                ItemAnimeCharacter(
//                  modifier = ItemAnimeCharacterConfig.default,
//                  thumbnailHeight = ItemAnimeCharacterConfig.ThumbnailHeightFour,
//                  title = it.name,
//                  imageUrl = it.imageUrl
//                )
//              }
//              showItemVerticalAnimeMoreWhenPastLimit(
//                modifier = ItemAnimeCharacterConfig.default,
//                thumbnailHeight = ItemAnimeCharacterConfig.ThumbnailHeightFour,
//                size = animeCharacterListState.data.size,
//                onClick = action
//              )
//            }
//          }
        }

        item(key = Component.ContentSimilar) {
          ScrollableHorizontalContent(
            modifier = Modifier,
            shimmer = rememberShimmerCustomBounds(),
            headerTitle = Constant.SIMILAR,
            contentState = animeRecommendationsListState,
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
      }
    }
  }
}