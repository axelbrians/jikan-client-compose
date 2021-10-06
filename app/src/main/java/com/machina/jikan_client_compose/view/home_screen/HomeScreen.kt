package com.machina.jikan_client_compose.view.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.core.enum.ContentType.Anime
import com.machina.jikan_client_compose.core.enum.ContentType.valueOf
import com.machina.jikan_client_compose.data.model.ContentSearch
import com.machina.jikan_client_compose.data.model.AnimeTop
import com.machina.jikan_client_compose.ui.theme.*
import com.machina.jikan_client_compose.view.composable.ChipGroup
import com.machina.jikan_client_compose.view.composable.CustomTextField
import com.machina.jikan_client_compose.viewmodels.HomeViewModel
import timber.log.Timber

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {

    val topAnimeList: List<AnimeTop> by viewModel.topAnimeList.observeAsState(listOf())
    val searchList: List<ContentSearch> by viewModel.searchList.observeAsState(listOf())

    val isFetching: Boolean by viewModel.isFetching.observeAsState(false)

    val listState = rememberLazyListState()
    var selectedType by remember { mutableStateOf(Anime) }

    var searchQuery by remember {
        mutableStateOf("")
    }

    LaunchedEffect(viewModel) {
        viewModel.getTopAnimeList()
    }

    LaunchedEffect(searchQuery + selectedType.name) {
        viewModel.searchContentByQuery(searchQuery, selectedType)
        Timber.d("query $searchQuery type ${selectedType.name.lowercase()}")
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

            Divider(color = BlackLighterBackground, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))

            if (searchQuery.isNotEmpty()) {
                ChipGroup(
                    selectedType = selectedType,
                    onSelectedChanged = { selectedType = valueOf(it) }
                )
                AnimeSearchList(
                    navController = navController,
                    listState = listState,
                    dataSet = searchList,
                    isFetching = isFetching
                )
                if (listState.isScrolledToTheEnd()) {
                    LaunchedEffect(searchQuery) {
                        Timber.d("query next page with $searchQuery")
                        viewModel.nextContentPageByQuery(searchQuery, selectedType)
                    }
                }
            } else {
                HomeContentList(
                    navController = navController,
                    topAnimeList = topAnimeList
                )
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

fun LazyListState.isScrolledToTheEnd(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}
