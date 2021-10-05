package com.machina.jikan_client_compose.view.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.data.model.AnimeSearch
import com.machina.jikan_client_compose.data.model.AnimeTop
import com.machina.jikan_client_compose.ui.navigation.MainNavigation.ANIME_DETAILS_SCREEN
import com.machina.jikan_client_compose.ui.theme.*
import com.machina.jikan_client_compose.view.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.view.composable.CustomTextField
import com.machina.jikan_client_compose.viewmodels.HomeViewModel

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {

    val topAnimeList: List<AnimeTop> by viewModel.topAnimeList.observeAsState(listOf())
    val animeSearchList: List<AnimeSearch> by viewModel.searchAnimeList.observeAsState(listOf())

    val isFetching: Boolean by viewModel.isFetching.observeAsState(false)

    var searchQuery by remember {
        mutableStateOf("")
    }

    LaunchedEffect(viewModel) {
        viewModel.getTopAnimeList()
    }

    LaunchedEffect(searchQuery) {
        viewModel.searchAnimeByQuery(searchQuery)
    }
    
    Scaffold {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 12.dp),
                fieldValue = searchQuery,
                onFieldValueChange = { searchQuery = it },
                fieldPlaceholder = "Try 'One Piece'",
                fieldPadding = PaddingValues(12.dp),
                leadingIcon = { SearchLeadingIcon() },
                paddingLeadingIconEnd = 8.dp,
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        SearchTrailingIcon(size = 16.dp, onClick = { searchQuery = "" })
                    }
                }
            )

            Divider(color = BlackLighterBackground, thickness = 1.dp, modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 8.dp))

            if (isFetching) {
                CenterCircularProgressIndicator(color = Yellow500, strokeWidth = 4.dp, size = 40.dp)
            } else {
                if (searchQuery.isNotEmpty()) {
                    AnimeSearchList(
                        navController = navController,
                        dataSet = animeSearchList
                    )
                } else {
                    HomeContentList(
                        navController = navController,
                        topAnimeList = topAnimeList
                    )
                }
            }
        }
    }
}

@Composable
fun SearchLeadingIcon() {
    Icon(Icons.Default.Search, "Search")
}

@Composable
fun SearchTrailingIcon(
    size: Dp = 24.dp,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.then(Modifier.size(size)),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "Close",
            tint = Grey
        )
    }
}