package com.machina.jikan_client_compose.presentation.content_search_screen

import android.view.Window
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute
import com.machina.jikan_client_compose.ui.theme.MyColor

fun NavGraphBuilder.searchNav(
  systemUiController: SystemUiController,
  window: Window,
  navController: NavController,
  dispatchers: DispatchersProvider
) {
  composable(route = MainNavigationRoute.CONTENT_SEARCH_SCREEN) { backstack ->
    OnDestinationChanged(
      systemUiController = systemUiController,
      color = MyColor.DarkBlueBackground,
      drawOverStatusBar = false,
      window = window,
    )

    SearchScreen(
      navigator = SearchScreenNavigator(navController),
      viewModel = hiltViewModel(),
      dispatchers = dispatchers
    )
  }
}

class SearchScreenNavigator(
  private val navController: NavController
) {

  fun navigateToContentDetailsScreen(
    malId: Int,
    contentType: ContentType
  ) {
    navController.navigate("${MainNavigationRoute.CONTENT_DETAILS_SCREEN}/${contentType.name}/$malId".lowercase())
  }

  fun navigateUp() {
    navController.navigateUp()
  }
}