package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.home_screen.composable.ItemAnime
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemAnimeAiringPopular
import com.machina.jikan_client_compose.presentation.home_screen.composable.ItemAnimeSchedule
import com.machina.jikan_client_compose.presentation.home_screen.composable.ItemAnimeTopShimmer
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeAiringPopularState
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeScheduleState
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeTopState
import com.machina.jikan_client_compose.presentation.home_screen.view_holder.ItemAnimeHorizontalPager
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow
import kotlin.math.absoluteValue

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ProfilePicture(modifier: Modifier = Modifier) {
  Card(
    modifier = modifier,
    shape = CircleShape,
    border = BorderStroke(4.dp, MaterialTheme.colors.surface)
  ) {
    Image(
      painter = rememberImagePainter(data = "https://cdn.myanimelist.net/images/anime/1948/120625.jpg"),
      contentDescription = null,
      modifier = Modifier.size(72.dp),
    )
  }
}

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun HomeContentList(
  animeAiringPopularState: AnimeAiringPopularState = AnimeAiringPopularState(),
  animeScheduleState: AnimeScheduleState = AnimeScheduleState(),
  animeTopState: AnimeTopState = AnimeTopState(),
  lazyColumnState: LazyListState = rememberLazyListState(),
  onTopAnimeClick: (String, Int) -> Unit
) {

  LazyColumn (
    state = lazyColumnState
  ) {

    item(key = "horizontal_pager_demo") {
      HorizontalPager(
        modifier = Modifier
          .fillMaxWidth()
          .height(280.dp),
        count = animeAiringPopularState.data.size,
        itemSpacing = 12.dp,
        contentPadding = PaddingValues(horizontal = 48.dp),
        key = { page -> animeAiringPopularState.data[page].malId }
      ) { page ->
        val data = animeAiringPopularState.data[page]
        val imagePainter = rememberImagePainter(data = data.imageUrl)
        Surface(
          color = Color.Transparent,
          modifier = Modifier
            .graphicsLayer {
              // Calculate the absolute offset for the current page from the
              // scroll position. We use the absolute value which allows us to mirror
              // any effects for both directions
              val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

              // We animate the scaleX + scaleY, between 85% and 100%
              lerp(
                start = 0.85f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
              ).also { scale ->
                scaleX = scale
                scaleY = scale
              }

              // We animate the alpha, between 50% and 100%
              alpha = lerp(
                start = 0.5f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
              )
            }
            .fillMaxSize()
            .aspectRatio(1.3f)
        ) {
          ItemAnimeHorizontalPager(
            modifier = Modifier,
            data = data,
            imagePainter = imagePainter,
            onClick = { onTopAnimeClick(ContentType.Anime.name, data.malId) })
        }
      }
    }


    // Start of Currently popular anime
    item(key = "anime_airing_popular_list") {
      Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          modifier = Modifier.weight(1f),
          text = "Currently popular",
          style = TextStyle(
            color = MyColor.Yellow500,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
        )

        IconButton(onClick = { }) {
          Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "See all",
            tint = MyColor.Grey
          )
        }
      }


      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
          val position = layoutCoordinates.unclippedBoundsInWindow()
          shimmerInstance.updateBounds(position)
        }
      ) {
        if (animeAiringPopularState.isLoading) {
          showShimmerPlaceholder(shimmerInstance)
        } else {
          items(animeAiringPopularState.data, key = { item -> item.malId }) { anime ->
            ItemAnimeAiringPopular(
              modifier = Modifier
                .width(160.dp)
                .padding(12.dp, 0.dp),
              anime = anime,
              onItemClick = { onTopAnimeClick(ContentType.Anime.name, anime.malId) }
            )
          }
        }
      }
    }

    // Start of Anime Airing Today
    item(key = "anime_schedule_list") {
      Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          modifier = Modifier.weight(1f),
          text = "Airing today",
          style = TextStyle(
            color = MyColor.Yellow500,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
        )

        IconButton(onClick = { }) {
          Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "See all",
            tint = MyColor.Grey
          )
        }
      }


      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
          val position = layoutCoordinates.unclippedBoundsInWindow()
          shimmerInstance.updateBounds(position)
        }
      ) {
        if (animeScheduleState.isLoading) {
          showShimmerPlaceholder(shimmerInstance)
        } else {
          items(animeScheduleState.data, key = { item -> item.malId }) { anime ->
            ItemAnimeSchedule(
              modifier = Modifier
                .width(160.dp)
                .padding(12.dp, 0.dp),
              anime = anime,
              onItemClick = { onTopAnimeClick(ContentType.Anime.name, anime.malId) }
            )
          }
        }
      }
    }
    // End of Anime Airing Today


    // Start of Top Anime of All Times
    item(key = "anime_top_list") {
      Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          modifier = Modifier.weight(1f),
          text = "Top Anime of All Times",
          style = TextStyle(
            color = MyColor.Yellow500,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
        )

        IconButton(onClick = { }) {
          Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "See all",
            tint = MyColor.Grey
          )
        }
      }

      val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp),
        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
          val position = layoutCoordinates.unclippedBoundsInWindow()
          shimmerInstance.updateBounds(position)
        }
      ) {
        if (animeTopState.isLoading) {
          showShimmerPlaceholder(shimmerInstance)
        } else {
          items(animeTopState.data, key = { item -> item.malId }) { anime ->
            ItemAnime(
              modifier = Modifier
                .width(160.dp)
                .padding(12.dp, 0.dp),
              anime = anime,
              onItemClick = { onTopAnimeClick(ContentType.Anime.name, anime.malId) }
            )
          }
        }

      }
    }
    // End of Top Anime of All Times



  }
}

private fun LazyListScope.showShimmerPlaceholder(shimmerInstance: Shimmer, count: Int = 5) {
  items(count) {
    ItemAnimeTopShimmer(shimmerInstance)
  }
}