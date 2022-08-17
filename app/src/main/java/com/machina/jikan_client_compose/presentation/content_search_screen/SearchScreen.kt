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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.extensions.isScrolledToTheEnd
import com.machina.jikan_client_compose.presentation.composable.MyDivider
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchBoxSearchScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.data.SearchScreenViewModel
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.darkBlue
import com.machina.jikan_client_compose.ui.theme.Type.medium
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalCoilApi::class, ExperimentalAnimationApi::class)
@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  navigator: SearchScreenNavigator,
  viewModel: SearchScreenViewModel,
  dispatchers: DispatchersProvider
) {

  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()

  val selectedType = rememberSaveable { mutableStateOf(ContentType.Anime) }
  val searchQuery = rememberSaveable { mutableStateOf("") }

  val job = remember { mutableStateOf<Job?>(null) }
  val snackbarHostState = remember { SnackbarHostState() }
  val focusRequester = remember { (FocusRequester()) }
  val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }




  val contentSearchState = viewModel.contentSearchState.value

  LaunchedEffect(key1 = viewModel.hashCode()) {
    focusRequester.requestFocus()
  }

  BackHandler(enabled = true) {
    navigator.navigateUp()
  }
  // TODO: Create button to open filter BottomSheet
  // TODO: Create filter UI BottomSheet
  // TODO: Create ViewModel for fetching available filter options
  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(MyColor.DarkBlueBackground),
    floatingActionButton = {
      ExpandableFloatingButtonSearchScreen()
    },
    scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      SearchBoxSearchScreen(
        modifier = Modifier.padding(12.dp),
        searchQuery = searchQuery.value,
        focusRequester = focusRequester,
        onSearchQueryCleared = { searchQuery.value = "" },
        onSearchQueryChanged = {
          searchQuery.value = it
          job.value?.cancel()
          job.value = scope.launch(dispatchers.default) {
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

      if (listState.isScrolledToTheEnd()) {
        LaunchedEffect(searchQuery.value) {
//          Timber.d("query next page with $searchQuery.value")
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

@Composable
fun ExpandableFloatingButtonSearchScreen(
  modifier: Modifier = Modifier,
  isExpanded: Boolean = false,
) {
  ExtendedFloatingActionButton(
    text = {
      Text(
        text = "Filter",
        style = Type.Typography.subtitle1.medium().darkBlue()
      )
    },
    icon = {
      Icon(
        imageVector = ImageVector.vectorResource(MyIcons.Solid.Filter),
        contentDescription = "Filter",
        tint = MyColor.DarkBlueBackground
      )
    },
    shape = RoundedCornerShape(12.dp),
    backgroundColor = MyColor.Yellow500,
    onClick = { /*TODO*/ }
  )
}