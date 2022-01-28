package com.machina.jikan_client_compose.presentation.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.core.enum.ContentType.Anime
import com.machina.jikan_client_compose.core.enum.ContentType.valueOf
import com.machina.jikan_client_compose.ui.theme.*
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.composable.ChipGroup
import com.machina.jikan_client_compose.presentation.composable.CustomTextField
import com.machina.jikan_client_compose.presentation.home_screen.composable.SearchEditText
import com.machina.jikan_client_compose.presentation.home_screen.data.HomeViewModel
import com.machina.jikan_client_compose.presentation.search_screen.composable.SearchLeadingIcon
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@InternalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
  navController: NavController,
  viewModel: HomeViewModel,
  lazyColumnState: LazyListState = rememberLazyListState(),
  onSearchFieldClick: (() -> Unit) = { },
  onContentClick: ((String, Int) -> Unit) = { type, id -> },
) {

  val animeScheduleState = viewModel.animeScheduleState.value
  val animeTopState = viewModel.animeTopState.value

  val snackbarHostState = remember { SnackbarHostState() }
  val searchQuery = remember { mutableStateOf("") }

  // Controlling snackbar on error. Only show one snackbar at a time with channel.
  // Channel create something like queue, so no snackbar will be showed when one is still showing.
  val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }


  LaunchedEffect(viewModel) {
    viewModel.getTodayAnimeSchedule()
    viewModel.getTopAnimeList()
  }

//  LaunchedEffect(searchQuery.value + selectedType.value.name) {
//    delay(1000L)
//    viewModel.searchContentByQuery(selectedType.value, searchQuery.value)
//    Timber.d("query $searchQuery.value type ${selectedType.value.name.lowercase()}")
//  }

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
            .padding(24.dp, 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
              onSearchFieldClick()
            },
          padding = PaddingValues(12.dp),
          content = {
            SearchEditText(
              fieldPlaceholder = "Try 'One Piece'",
            )
          },
          leadingIcon = {
            SearchLeadingIcon(
              size = 16.dp,
              padding = PaddingValues(end = 8.dp)
            )
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

      HomeContentList(
        animeScheduleState = animeScheduleState,
        animeTopState = animeTopState,
        onTopAnimeClick = onContentClick,
        lazyColumnState = lazyColumnState
      )
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