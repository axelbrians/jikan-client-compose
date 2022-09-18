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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.composable.content_horizontal.ScrollableHorizontalContent
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.*
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsNavArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsScreenNavigator
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.ui.shimmer.rememberShimmerCustomBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
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
          val shimmerInstance = rememberShimmerCustomBounds()
          ScrollableHorizontalContent(
            modifier = Modifier,
            itemModifier = ItemVerticalAnimeModifier.Small,
            shimmer = shimmerInstance,
            thumbnailHeight = ItemVerticalAnimeModifier.ThumbnailHeightSmall,
            headerTitle = Constant.CHARACTERS,
            contentState = animeCharacterListState,
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

        item(key = ContentDetailsScreenSection.ContentSimilar) {
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