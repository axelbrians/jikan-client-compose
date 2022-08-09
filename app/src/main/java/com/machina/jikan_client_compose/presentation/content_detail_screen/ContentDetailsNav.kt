package com.machina.jikan_client_compose.presentation.content_detail_screen

import android.view.Window
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute
import com.machina.jikan_client_compose.ui.navigation.content_view_all.ContentViewAllType
import com.machina.jikan_client_compose.ui.theme.MyColor

private val detailsScreenArgs = listOf(
  navArgument(name = "contentType") {
    type = NavType.StringType
    nullable = false
  },
  navArgument(name = "malId") {
    type = NavType.IntType
    nullable = false
  }
)

@OptIn(ExperimentalAnimationApi::class, ExperimentalCoilApi::class)
fun NavGraphBuilder.contentDetailsNav(
  systemUiController: SystemUiController,
  window: Window,
  navController: NavController
) {
  composable(
    route = "${MainNavigationRoute.CONTENT_DETAILS_SCREEN}/{contentType}/{malId}",
    arguments = detailsScreenArgs
  ) { backStack ->
    OnDestinationChanged(
      systemUiController = systemUiController,
      color = MyColor.Transparent,
      drawOverStatusBar = true,
      window = window,
    )

    ContentDetailsScreen(
      navigator = ContentDetailsScreenNavigator(navController),
      viewModel = hiltViewModel(),
      backStack.arguments?.getString("contentType")?.replaceFirstChar { it.uppercase() },
      backStack.arguments?.getInt("malId")
    )
  }

}

class ContentDetailsScreenNavigator(
  private val navController: NavController
) {

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

  fun navigateUp(): Boolean {
    return navController.navigateUp()
  }
}