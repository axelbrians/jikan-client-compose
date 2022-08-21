package com.machina.jikan_client_compose.presentation.content_search_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.extensions.isScrolledToTheEnd
import com.machina.jikan_client_compose.core.extensions.isScrollingUp
import com.machina.jikan_client_compose.presentation.composable.MyDivider
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.ExpandableFloatingButtonSearchScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.FilterBottomSheetSearchScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchBoxSearchScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.data.SearchScreenViewModel
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalCoilApi::class, ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  navigator: SearchScreenNavigator,
  viewModel: SearchScreenViewModel,
  dispatchers: DispatchersProvider
) {

  val selectedType = rememberSaveable { mutableStateOf(ContentType.Anime) }
  val searchQuery = rememberSaveable { mutableStateOf("") }

  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  val scaffoldState = rememberModalBottomSheetState(
    initialValue = ModalBottomSheetValue.Hidden
  )

  val job = remember { mutableStateOf<Job?>(null) }
  val focusRequester = remember { (FocusRequester()) }
  val snackbarHostState = remember { SnackbarHostState() }
  val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }


  val contentSearchState = viewModel.contentSearchState.value

  LaunchedEffect(key1 = viewModel.hashCode()) {
//    focusRequester.requestFocus()
  }

  BackHandler(enabled = true) {
    if (scaffoldState.isVisible) {
      coroutineScope.launch {
        scaffoldState.hide()
      }
    } else {
      navigator.navigateUp()
    }
  }
//  rating: g pg pg13 r17 r rx
//  g all ages
//  pg children
//  pg13 teens older 13
//  r17 violence
//  r mild nudity
//  rx hentai
//
//  status: airing complete upcoming
//
//  order_by: al_id title type rating start_date end_date episodes score scored_by rank popularity members favorites
//
//  sort: desc asc
//
//  type: tv movie ova special ona music
//
//  sfw: boolean (filter adult entries)
//
//  genres: id of genre with comma as delimitter 1,2,3 etc
//
//  genres_exclude: id of genre with comma as delimitter 1,2,3 etc
  // TODO: Create filter UI BottomSheet
  // TODO: Create ViewModel for fetching available filter options
  ModalBottomSheetLayout(
    modifier = Modifier
      .fillMaxSize()
      .background(MyColor.DarkBlueBackground),
    scrimColor = Color(0, 0, 0, 150),
    sheetState = scaffoldState,
    sheetShape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp),
    sheetContent = { FilterBottomSheetSearchScreen() }
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      Column(modifier = Modifier.fillMaxWidth()) {
        SearchBoxSearchScreen(
          modifier = Modifier.padding(12.dp),
          searchQuery = searchQuery.value,
          focusRequester = focusRequester,
          onSearchQueryCleared = { searchQuery.value = "" },
          onSearchQueryChanged = {
            searchQuery.value = it
            job.value?.cancel()
            job.value = coroutineScope.launch(dispatchers.default) {
              delay(1000L)
              viewModel.searchContentByQuery(selectedType.value, searchQuery.value)
              Timber.d("query $searchQuery.value type ${selectedType.value.name.lowercase()}")
            }
          }
        )

        MyDivider.Horizontal.DarkGreyBackground(PaddingValues(bottom = 8.dp))

//      ChipGroup(
//        selectedType = selectedType.value,
//        onSelectedChanged = { selectedType.value = ContentType.valueOf(it) }
//      )
        ContentSearchList(
          listState = listState,
          state = contentSearchState,
          onItemClick = navigator::navigateToContentDetailsScreen
        )
      }

      ExpandableFloatingButtonSearchScreen(
        modifier = Modifier
          .padding(bottom = 16.dp, end = 16.dp)
          .align(Alignment.BottomEnd),
        isExpanded = listState.isScrollingUp(),
        onClick = {
          coroutineScope.launch {
            scaffoldState.show()
          }
        }
      )
    }

    if (listState.isScrolledToTheEnd()) {
      LaunchedEffect(searchQuery.value) {
//          Timber.d("query next page with $searchQuery.value")
        viewModel.nextContentPageByQuery(searchQuery.value, selectedType.value)
      }
    }

    // Try to emmit error message to snackbarChannel if not have been handled before.
    with(contentSearchState.error.getContentIfNotHandled()) {
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