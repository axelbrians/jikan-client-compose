package com.machina.jikan_client_compose.presentation.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
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
import com.machina.jikan_client_compose.presentation.home_screen.data.HomeViewModel
import com.machina.jikan_client_compose.ui.theme.MyColor.Grey
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@InternalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
  navController: NavController,
  viewModel: HomeViewModel,
  lazyColumnState: LazyListState = rememberLazyListState(),
  onContentClick: (String, Int) -> Unit,
) {

  val animeTopState = viewModel.animeTopState.value
  val contentSearchState = viewModel.contentSearchState.value

  val listState = rememberLazyListState()
  val snackbarHostState = remember { SnackbarHostState() }
  val selectedType = remember { mutableStateOf(Anime) }
  val searchQuery = remember { mutableStateOf("") }

  // Controlling snackbar on error. Only show one snackbar at a time with channel.
  // Channel create somethign like queue, so no snackbar will be showed when one is still showing.
  val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }


  LaunchedEffect(viewModel) { viewModel.getTopAnimeList() }

  LaunchedEffect(searchQuery.value + selectedType.value.name) {
    delay(1000L)
    viewModel.searchContentByQuery(selectedType.value, searchQuery.value)
    Timber.d("query $searchQuery.value type ${selectedType.value.name.lowercase()}")
  }

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(MyColor.BlackBackground),
    scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
  ) {
    Column(
      modifier = Modifier.fillMaxWidth()
    ) {
      CustomTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp, 12.dp),
        fieldValue = searchQuery.value,
        fieldPlaceholder = "Try 'One Piece'",
        fieldPadding = PaddingValues(12.dp),
        onFieldValueChange = { searchQuery.value = it },
        leadingIcon = { SearchLeadingIcon() },
        paddingLeadingIcon = PaddingValues(end = 8.dp),
        trailingIcon = {
          if (searchQuery.value.isNotEmpty()) {
            SearchTrailingIcon(size = 16.dp, onClick = { searchQuery.value = "" })
          }
        }
      )

      BackHandler(enabled = (searchQuery.value.isNotEmpty())) {
        searchQuery.value = ""
      }

      Divider(
        color = MyColor.BlackLighterBackground,
        thickness = 1.dp,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      if (searchQuery.value.isNotEmpty()) {
        ChipGroup(
          selectedType = selectedType.value,
          onSelectedChanged = { selectedType.value = valueOf(it) }
        )
        ContentSearchList(
          listState = listState,
          state = contentSearchState,
          onItemClick = onContentClick
        )

        if (listState.isScrolledToTheEnd()) {
          LaunchedEffect(searchQuery.value) {
            Timber.d("query next page with $searchQuery.value")
            viewModel.nextContentPageByQuery(searchQuery.value, selectedType.value)
          }
        }
      } else {
        HomeContentList(
          topAnimeList = animeTopState.data,
          onContentClick = onContentClick,
          lazyColumnState = lazyColumnState
        )
      }

      // Loading Indicator while fetching data
      if (animeTopState.isLoading) {
        CenterCircularProgressIndicator(strokeWidth = 4.dp, size = 40.dp)
      }
    }

    // Try to emmit error message to snackbarChannel if not have been handled before.
    with(animeTopState.error.getContentIfNotHandled()) {
      snackbarChannel.trySend(this)
    }

    // Side-effect to control how snackbar should showing
    LaunchedEffect(snackbarChannel) {
      snackbarChannel.receiveAsFlow().collect { error ->

        val result = if (error != null) {
          snackbarHostState.showSnackbar(
            message = error,
            actionLabel = "Dismiss"
          )
        } else {
          null
        }

        when (result) {
          SnackbarResult.ActionPerformed -> {
            /* action has been performed */
          }
          SnackbarResult.Dismissed -> {
            /* dismissed, no action needed */
          }
        }
      }
    }
  }
}

@Composable
fun SearchLeadingIcon() {
  Icon(
    Icons.Default.Search,
    "Search",
    tint = MyColor.Grey
  )
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
      tint = MyColor.Grey
    )
  }
}

fun LazyListState.isScrolledToTheEnd(): Boolean {
  return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}
