package com.machina.jikan_client_compose.presentation.content_detail_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.data.remote.dto_v4.common.Jpg.Companion.getHighestResImgUrl
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.composable.HorizontalContentHeader
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsScreenToolbar
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsSynopsis
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsThreeColumnSection
import com.machina.jikan_client_compose.presentation.content_detail_screen.composable.ContentDetailsTrailerPlayer
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.presentation.content_detail_screen.item.ItemAnimeCharacter
import com.machina.jikan_client_compose.presentation.content_detail_screen.item.ItemAnimeCharacterConfig
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsNavArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsScreenNavigator
import com.machina.jikan_client_compose.presentation.home_screen.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnime
import com.machina.jikan_client_compose.presentation.home_screen.item.ItemVerticalAnimeModifier
import com.machina.jikan_client_compose.presentation.home_screen.item.showItemVerticalAnimeMoreWhenPastLimit
import com.machina.jikan_client_compose.presentation.home_screen.item.showItemVerticalAnimeShimmer
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
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
  val contentDetailsState = viewModel.contentDetailsState.value
  val animeCharacterListState = viewModel.animeCharactersListState.value
  val animeRecommendationsListState = viewModel.animeRecommendationsListState.value
  val genres = contentDetailsState.data?.genres ?: listOf()

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

    if (contentDetailsState.isLoading) {
      CenterCircularProgressIndicator(
        size = 40.dp,
        color = MyColor.Yellow500
      )
    } else {
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
            onClick = { isSynopsisExpanded = it }
          )
        }


        // Genre FlowRow Chips
        item(key = Component.ContentGenre) {
          if (genres.isNotEmpty()) {
            if (isSynopsisExpanded) {
              FlowRow(
                modifier = Modifier.padding(horizontal = 10.dp),
                lastLineMainAxisAlignment = FlowMainAxisAlignment.Start
              ) {
                genres.forEach {
                  GenreChip(text = it.name)
                }
              }
            } else {
              LazyRow(
                contentPadding = PaddingValues(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Start
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
              trailerUrl = contentDetailsState.data.trailer.embedUrl
            )
          }
        }

        // Anime Characters List
        item(key = Component.ContentCharacters) {
          val shimmerInstance = rememberShimmerCustomBounds()

          HorizontalContentHeader(
            modifier = Modifier
              .fillMaxWidth()
              .padding(start = 18.dp, end = 12.dp, top = 8.dp, bottom = 4.dp),
            title = Constant.CHARACTERS,
            onButtonClick = { }
          )

          LazyRow(
            modifier = Modifier.onUpdateShimmerBounds(shimmerInstance),
            contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp)
          ) {
            items(
              items = animeCharacterListState.data.take(Constant.HORIZONTAL_CHARACTERS_LIMIT),
              key = { it.malId }
            ) {
              ItemAnimeCharacter(
                modifier = ItemAnimeCharacterConfig.default,
                data = it
              )
            }
          }
        }

        item(key = Component.ContentSimilar) {
          val shimmerInstance = rememberShimmerCustomBounds()
          val action = {
            navigator.navigateToContentViewAllScreen(
              "${Constant.SIMILAR} to ${contentDetailsState.data?.title}",
              Endpoints.getAnimeRecommendationEndpoint(contentDetailsState.data?.malId ?: 0)
            )
          }

          if (animeRecommendationsListState.isLoading) {
            ContentListHeaderWithButtonShimmer(shimmerInstance)
          } else {
            HorizontalContentHeader(
              modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 12.dp, top = 8.dp, bottom = 4.dp),
              title = Constant.SIMILAR,
              onButtonClick = action
            )
          }

          LazyRow(
            modifier = Modifier.onUpdateShimmerBounds(shimmerInstance),
            contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp)
          ) {
            if (animeRecommendationsListState.isLoading) {
              showItemVerticalAnimeShimmer(shimmerInstance)
            } else {
              items(
                items = animeRecommendationsListState.data.take(Constant.HORIZONTAL_CONTENT_LIMIT),
                key = { it.malId }
              ) {
                ItemVerticalAnime(
                  modifier = ItemVerticalAnimeModifier.default,
                  data = it,
                  onClick = navigator::navigateToContentDetailsScreen
                )
              }
              showItemVerticalAnimeMoreWhenPastLimit(
                modifier = ItemVerticalAnimeModifier.default,
                size = animeRecommendationsListState.data.size,
                onClick = action
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun GenreChip(
  modifier: Modifier = Modifier,
  text: String = ""
) {
  Surface(
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
    shape = RoundedCornerShape(16.dp),
    color = MyColor.Yellow500,
  ) {
    Text(
      text = text,
      style = TextStyle(
        color = MyColor.DarkBlueBackground,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
      ),
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
    )
  }
}