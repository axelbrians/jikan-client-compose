package com.machina.jikan_client_compose.presentation.content_detail_screen

import android.view.Window
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsNavArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsScreenNavigator
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalAnimationApi::class, ExperimentalCoilApi::class)
@Destination
@Composable
fun ContentDetailsNav(
  systemUiController: SystemUiController,
  window: Window,
  navigator: DestinationsNavigator,
  navArgs: ContentDetailsNavArgs
) {
  OnDestinationChanged(
    systemUiController = systemUiController,
    color = MyColor.Transparent,
    drawOverStatusBar = true,
    window = window,
  )

  ContentDetailsScreen(
    navigator = ContentDetailsScreenNavigator(navigator),
    viewModel = hiltViewModel(),
    navArgs = navArgs
  )
}