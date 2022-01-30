package com.machina.jikan_client_compose.presentation.search_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.presentation.composable.ChipGroup
import com.machina.jikan_client_compose.presentation.composable.CustomTextField
import com.machina.jikan_client_compose.presentation.extension.isScrolledToTheEnd
import com.machina.jikan_client_compose.presentation.search_screen.composable.SearchEditText
import com.machina.jikan_client_compose.presentation.search_screen.composable.SearchLeadingIcon
import com.machina.jikan_client_compose.presentation.search_screen.composable.SearchTrailingIcon
import com.machina.jikan_client_compose.presentation.search_screen.data.SearchScreenViewModel
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  viewModel: SearchScreenViewModel,
  onContentClick: ((String, Int) -> Unit) = { type, id -> },
) {

  val listState = rememberLazyListState()
  val snackbarHostState = remember { SnackbarHostState() }
  val selectedType = remember { mutableStateOf(ContentType.Anime) }
  val searchQuery = remember { mutableStateOf("") }
  val focusRequester = remember { FocusRequester() }
  val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }


  val contentSearchState = viewModel.contentSearchState.value

  LaunchedEffect(key1 = viewModel.hashCode()) {
    focusRequester.requestFocus()
  }

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
    Column(modifier = Modifier.fillMaxWidth()) {
      Box(modifier = Modifier.fillMaxWidth()) {
        CustomTextField(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 12.dp),
          padding = PaddingValues(12.dp),
          content = {
            SearchEditText(
              value = searchQuery.value,
              placeholder = "Try 'One Piece'",
              focusRequester = focusRequester,
              onValueChange = {
                searchQuery.value = it
              }
            )
          },
          leadingIcon = {
            SearchLeadingIcon(
              size = 16.dp,
              padding = PaddingValues(end = 8.dp)
            )
          },
          trailingIcon = {
            if (searchQuery.value.isNotEmpty()) {
              SearchTrailingIcon(
                size = 16.dp,
                padding = PaddingValues(start = 8.dp),
                onClick = { searchQuery.value = "" })
            }
          }
        )

      }

      BackHandler(enabled = (searchQuery.value.isNotEmpty())) {
        searchQuery.value = ""
      }

      Divider(
        color = MyColor.BlackLighterBackground,
        thickness = 1.dp,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      ChipGroup(
        selectedType = selectedType.value,
        onSelectedChanged = { selectedType.value = ContentType.valueOf(it) }
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