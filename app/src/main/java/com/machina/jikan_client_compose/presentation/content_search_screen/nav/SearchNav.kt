package com.machina.jikan_client_compose.presentation.content_search_screen

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.presentation.content_search_screen.nav.SearchScreenNavigator
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchNav(
  systemUiController: SystemUiController,
  window: Window,
  navigator: DestinationsNavigator
) {
  OnDestinationChanged(
    systemUiController = systemUiController,
    color = MyColor.DarkBlueBackground,
    drawOverStatusBar = false,
    window = window,
  )

  SearchScreen(
    navigator = SearchScreenNavigator(navigator),
    viewModel = hiltViewModel()
  )
}