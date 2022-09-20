package com.machina.jikan_client_compose.presentation.content_detail_screen

import android.view.Window
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
  Box(modifier = Modifier.fillMaxSize()) {
    ContentDetailsScreen(
      navigator = ContentDetailsScreenNavigator(navigator),
      viewModel = hiltViewModel(),
      navArgs = navArgs
    )
  }
}