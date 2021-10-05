package com.machina.jikan_client_compose.view.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.data.model.AnimeSearch
import com.machina.jikan_client_compose.data.model.AnimeTop
import com.machina.jikan_client_compose.ui.navigation.MainNavigation

@ExperimentalCoilApi
@Composable
fun AnimeSearchList(
    navController: NavController,
    dataSet: List<AnimeSearch>
) {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp)
    ) {
        items(dataSet, key = { it.malId }) { data ->
            ItemAnimeSearch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 8.dp),
                data = data
            )
        }
    }
}