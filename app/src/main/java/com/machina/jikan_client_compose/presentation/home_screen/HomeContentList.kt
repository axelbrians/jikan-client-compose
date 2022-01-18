package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.domain.model.AnimeSchedule
import com.machina.jikan_client_compose.domain.model.AnimeTop
import com.machina.jikan_client_compose.presentation.home_screen.composable.ItemAnime
import com.machina.jikan_client_compose.presentation.home_screen.composable.ItemAnimeSchedule
import com.machina.jikan_client_compose.ui.theme.MyColor

@ExperimentalCoilApi
@Composable
fun HomeContentList(
  animeScheduleList: List<AnimeSchedule> = emptyList(),
  animeTopList: List<AnimeTop> = emptyList(),
  lazyColumnState: LazyListState = rememberLazyListState(),
  onTopAnimeClick: (String, Int) -> Unit
) {
  LazyColumn (
    state = lazyColumnState
  ) {
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

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp)
      ) {
        items(animeScheduleList, key = { item -> item.malId }) { anime ->
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

      LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp)
      ) {
        items(animeTopList, key = { item -> item.malId }) { anime ->
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
}