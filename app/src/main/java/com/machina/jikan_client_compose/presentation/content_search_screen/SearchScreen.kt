package com.machina.jikan_client_compose.presentation.content_search_screen

import androidx.activity.compose.BackHandler
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
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.extensions.isScrolledToTheEnd
import com.machina.jikan_client_compose.core.extensions.isScrollingUp
import com.machina.jikan_client_compose.presentation.composable.MyDivider
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.ContentSearchList
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchBoxSearchScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet.ExpandableFloatingButtonSearchScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet.FilterModalBottomSheet
import com.machina.jikan_client_compose.presentation.content_search_screen.data.SearchScreenViewModel
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.FilterEvent
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.SearchEvent
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import com.machina.jikan_client_compose.presentation.content_search_screen.nav.SearchScreenNavigator
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
  navigator: SearchScreenNavigator,
  viewModel: SearchScreenViewModel
) {

  val selectedType = rememberSaveable { mutableStateOf(ContentType.Anime) }
  val searchQuery = rememberSaveable { mutableStateOf("") }

  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  val sheetState = rememberModalBottomSheetState(
    initialValue = ModalBottomSheetValue.Hidden
  )

  val focusRequester = remember { (FocusRequester()) }
  val snackbarHostState = remember { SnackbarHostState() }
  val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }

  val contentSearchState = viewModel.contentSearchState.value
  val filterSearchState = viewModel.searchFilterState.value

  LaunchedEffect(key1 = viewModel.hashCode()) {
    focusRequester.requestFocus()
    viewModel.getFilterData()
  }

  BackHandler(enabled = true) {
    if (sheetState.isVisible) {
      coroutineScope.launch {
        sheetState.hide()
      }
    } else {
      navigator.navigateUp()
    }
  }

  ModalBottomSheetLayout(
    modifier = Modifier
      .fillMaxSize()
      .background(MyColor.DarkBlueBackground),
    scrimColor = Color.Black.copy(alpha = 0.6f),
    sheetState = sheetState,
    sheetShape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp),
    sheetContent = {
      FilterModalBottomSheet(
        mapFilter = filterSearchState.data,
        onFilterChanged = { data: FilterGroupData ->
          viewModel.onFilterEvent(
            FilterEvent.FilterChanged(data)
          )
        },
        onFilterReset = {
          viewModel.onFilterEvent(
            FilterEvent.FilterReset(selectedType.value, searchQuery.value)
          )
          coroutineScope.launch { sheetState.hide() }
        },
        onFilterApplied = {
          viewModel.onFilterEvent(
            FilterEvent.FilterApplied(selectedType.value, searchQuery.value)
          )
          coroutineScope.launch { sheetState.hide() }
        }
      )
    }
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
            viewModel.onSearchEvent(
              SearchEvent.SearchFirstPage(selectedType.value, searchQuery.value)
            )
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
            sheetState.show()
          }
        }
      )
    }

    if (listState.isScrolledToTheEnd()) {
      viewModel.onSearchEvent(
        SearchEvent.SearchNextPage(selectedType.value, searchQuery.value)
      )
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