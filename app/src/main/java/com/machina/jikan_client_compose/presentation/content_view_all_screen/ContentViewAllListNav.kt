package com.machina.jikan_client_compose.presentation.content_view_all_screen

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.presentation.content_view_all_screen.nav.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_screen.nav.ContentViewAllScreenNavigator
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ContentViewAllListNav(
  systemUiController: SystemUiController,
  window: Window,
  navigator: DestinationsNavigator,
  navArgs: ContentViewAllListNavArgs
) {
  OnDestinationChanged(
    systemUiController = systemUiController,
    color = MyColor.DarkGreyBackground,
    drawOverStatusBar = false,
    window = window
  )

  ContentViewAllListScreen(
    navigator = ContentViewAllScreenNavigator(navigator),
    viewModel = hiltViewModel(),
    navArgs = navArgs
  )
}