package com.machina.jikan_client_compose.presentation.home_screen

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsNavArgs
import com.machina.jikan_client_compose.presentation.destinations.ContentDetailsNavDestination
import com.machina.jikan_client_compose.presentation.destinations.SearchNavDestination
import com.machina.jikan_client_compose.ui.navigation.content_view_all.ContentViewAllType
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalCoroutinesApi::class, ExperimentalCoilApi::class)
@Destination(start = true)
@Composable
fun HomeScreenNav(
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

  HomeScreen(
    navigator = HomeScreenNavigator(navigator),
    viewModel = hiltViewModel()
  )
}


class HomeScreenNavigator(
  private val navigator: DestinationsNavigator
) {

  fun navigateToSearchScreen() {
    navigator.navigate(SearchNavDestination())
  }

  fun navigateToContentViewAllScreen(
    type: ContentViewAllType,
    title: String
  ) {
//    navController.navigate("${MainNavigationRoute.CONTENT_VIEW_ALL_SCREEN}/${type.name}/${title}")
  }

  fun navigateToContentDetailsScreen(
    malId: Int,
    contentType: ContentType
  ) {
    val destination = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
    navigator.navigate(destination)
  }
}