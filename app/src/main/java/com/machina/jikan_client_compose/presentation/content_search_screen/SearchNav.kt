package com.machina.jikan_client_compose.presentation.content_search_screen

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsNavArgs
import com.machina.jikan_client_compose.presentation.destinations.ContentDetailsNavDestination
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
    navigator = navigator,
    viewModel = hiltViewModel()
  )
}

class SearchScreenNavigator(
  private val navigator: DestinationsNavigator
) {

  fun navigateToContentDetailsScreen(
    malId: Int,
    contentType: ContentType
  ) {
    val direction = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
    navigator.navigate(direction)
//    navController.navigate("${MainNavigationRoute.CONTENT_DETAILS_SCREEN}/${contentType.name}/$malId".lowercase())
  }

  fun navigateUp() {
    navigator.navigateUp()
  }
}