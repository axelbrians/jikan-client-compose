package com.machina.jikan_client_compose.view.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.data.model.AnimeTop
import com.machina.jikan_client_compose.ui.navigation.MainNavigation
import com.machina.jikan_client_compose.ui.theme.Grey
import com.machina.jikan_client_compose.ui.theme.Yellow500

@ExperimentalCoilApi
@Composable
fun HomeContentList(
    navController: NavController,
    topAnimeList: List<AnimeTop>
) {
    Row(
        modifier = Modifier.padding(18.dp, 4.dp, 18.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Trending Now",
            style = TextStyle(color = Yellow500, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        )

        IconButton(onClick = {  }) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "See all", tint = Grey)
        }
    }

    LazyRow(
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp)
    ) {
        items(topAnimeList, key = { item ->  item.malId}) { anime ->
            ItemAnime(
                modifier = Modifier
                    .width(160.dp)
                    .padding(12.dp, 0.dp),
                anime = anime,
                onItemClick = { navController.navigate(MainNavigation.ANIME_DETAILS_SCREEN) }
            )
        }
    }
}