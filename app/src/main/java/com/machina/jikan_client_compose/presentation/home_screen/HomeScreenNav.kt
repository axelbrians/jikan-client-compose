package com.machina.jikan_client_compose.presentation.home_screen

import android.view.Window
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute
import com.machina.jikan_client_compose.ui.navigation.content_view_all.ContentViewAllType
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalCoroutinesApi::class, ExperimentalCoilApi::class)
fun NavGraphBuilder.homeScreenNav(
  systemUiController: SystemUiController,
  window: Window,
  navController: NavController
) {
  composable(route = MainNavigationRoute.HOME_SCREEN) {
    OnDestinationChanged(
      systemUiController = systemUiController,
      color = MyColor.BlackBackground,
      drawOverStatusBar = false,
      window = window,
    )

    HomeScreen(
      navigator = HomeScreenNavigator(navController),
      viewModel = hiltViewModel()
    )
  }
}


class HomeScreenNavigator(
  private val navController: NavController
) {

  fun navigateToSearchScreen() {
    navController.navigate(MainNavigationRoute.CONTENT_SEARCH_SCREEN)
  }

  fun navigateToContentViewAllScreen(
    type: ContentViewAllType,
    title: String
  ) {
    navController.navigate("${MainNavigationRoute.CONTENT_VIEW_ALL_SCREEN}/${type.name}/${title}")
  }

  fun navigateToContentDetailsScreen(
    malId: Int,
    contentType: ContentType
  ) {
    navController.navigate("${MainNavigationRoute.CONTENT_DETAILS_SCREEN}/${contentType.name}/$malId".lowercase())
  }
}