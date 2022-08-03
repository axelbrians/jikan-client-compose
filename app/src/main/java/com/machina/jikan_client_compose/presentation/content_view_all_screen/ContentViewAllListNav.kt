package com.machina.jikan_client_compose.presentation.content_view_all_screen



import android.view.Window
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllAnimeScheduleViewModel
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllAnimeTopViewModel
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllViewModel
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute
import com.machina.jikan_client_compose.ui.navigation.content_view_all.ContentViewAllType
import com.machina.jikan_client_compose.ui.theme.MyColor

fun NavGraphBuilder.contentViewAllListNav(
  systemUiController: SystemUiController,
  window: Window,
  navController: NavController
) {
  composable(
    route = "${MainNavigationRoute.CONTENT_VIEW_ALL_SCREEN}/{content}/{title}",
  ) { backStack ->
    OnDestinationChanged(
      systemUiController = systemUiController,
      color = MyColor.BlackBackground,
      drawOverStatusBar = false,
      window = window,
    )

    val title = backStack.arguments?.getString("title", "") ?: ""
    val content = ContentViewAllType.valueOf(
      backStack.arguments?.getString("content", "") ?: ""
    )

    val viewModel = when(content) {
      ContentViewAllType.AnimeSchedule -> hiltViewModel<ContentViewAllAnimeScheduleViewModel>()
      ContentViewAllType.AnimeTop -> hiltViewModel<ContentViewAllAnimeTopViewModel>()
      else -> null
    } as? ContentViewAllViewModel

    if (viewModel != null) {
      ContentViewAllListScreen(
        navigator = ContentViewAllScreenNavigator(navController),
        viewModel = viewModel,
        title = title
      )
    } else { // Close screen if content is not supported
      navController.popBackStack()
    }
  }
}

class ContentViewAllScreenNavigator(
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