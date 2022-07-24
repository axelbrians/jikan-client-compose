package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.presentation.composable.CustomTextField
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchLeadingIcon
import com.machina.jikan_client_compose.presentation.home_screen.composable.SearchEditText
import com.machina.jikan_client_compose.presentation.home_screen.viewmodel.HomeViewModel
import com.machina.jikan_client_compose.ui.navigation.navigator.HomeScreenNavigation
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalMaterialApi::class)
@InternalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
  navigation: HomeScreenNavigation,
  viewModel: HomeViewModel,
  lazyColumnState: LazyListState = rememberLazyListState(),
  onSearchFieldClick: (() -> Unit) = { }
) {

  val animeAiringPopularState = viewModel.animeAiringPopular.value
  val animeScheduleState = viewModel.animeScheduleState.value
  val animeTopState = viewModel.animeTopState.value

  val snackbarHostState = remember { SnackbarHostState() }


  // Controlling snackbar on error. Only show one snackbar at a time with channel.
  // Channel create something like queue, so no snackbar will be showed when one is still showing.
  val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }


  LaunchedEffect(viewModel) {
    viewModel.getAnimeAiringPopular()
    viewModel.getTodayAnimeSchedule()
    viewModel.getTopAnimeList()
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

      Divider(
        color = MyColor.BlackLighterBackground,
        thickness = 1.dp
      )

      HomeContentList(
        navigation = navigation,
        animeAiringPopularState = animeAiringPopularState,
        animeScheduleState = animeScheduleState,
        animeTopState = animeTopState,
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
            actionLabel = "Dismiss",
            duration = SnackbarDuration.Long
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