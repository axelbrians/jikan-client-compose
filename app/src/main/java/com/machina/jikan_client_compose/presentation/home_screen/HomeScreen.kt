package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.core.enum.ContentType.Anime
import com.machina.jikan_client_compose.core.enum.ContentType.valueOf
import com.machina.jikan_client_compose.ui.theme.*
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.composable.ChipGroup
import com.machina.jikan_client_compose.presentation.composable.CustomTextField
import com.machina.jikan_client_compose.viewmodels.HomeViewModel
import kotlinx.coroutines.delay
import timber.log.Timber

@ExperimentalCoilApi
@Composable
fun HomeScreen(
  navController: NavController,
  viewModel: HomeViewModel,
  onContentClick: (String, Int) -> Unit,
) {

  val animeTopState = viewModel.animeTopState.value
  val contentSearchState = viewModel.contentSearchState.value

  val listState = rememberLazyListState()
  var selectedType by remember { mutableStateOf(Anime) }
  var searchQuery by remember { mutableStateOf("") }

  LaunchedEffect(viewModel) { viewModel.getTopAnimeList() }

  LaunchedEffect(searchQuery + selectedType.name) {
    delay(500L)
    viewModel.searchContentByQuery(selectedType, searchQuery)
    Timber.d("query $searchQuery type ${selectedType.name.lowercase()}")
  }

  Box(
    modifier = Modifier.fillMaxSize()
      .background(BlackBackground)
  ) {
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
        ContentSearchList(
          listState = listState,
          state = contentSearchState,
          onItemClick = onContentClick
        )
        if (listState.isScrolledToTheEnd()) {
          LaunchedEffect(searchQuery) {
            Timber.d("query next page with $searchQuery")
            viewModel.nextContentPageByQuery(searchQuery, selectedType)
          }
        }
      } else {
        HomeContentList(
          topAnimeList = animeTopState.data,
          onContentClick = onContentClick
        )
      }

      if (animeTopState.isLoading) {
        CenterCircularProgressIndicator(strokeWidth = 4.dp, size = 40.dp)
      }

    }
  }
}

@Composable
fun SearchLeadingIcon() {
  Icon(Icons.Default.Search, "Search", tint = Grey)
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
