package com.machina.jikan_client_compose.ui.navigation.navigator

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute
import com.machina.jikan_client_compose.ui.navigation.content_view_all.ContentViewAllType

class HomeScreenNavigation(
  private val navController: NavController
) {
  fun navigateToContentViewAllScreen(type: ContentViewAllType) {
    navController.navigate("${MainNavigationRoute.CONTENT_VIEW_ALL_SCREEN}/${type.name}")
  }

  fun navigateToContentDetailsScreen(
    malId: Int,
    contentType: ContentType
  ) {
    navController.navigate("${MainNavigationRoute.CONTENT_DETAILS_SCREEN}/${contentType.name}/$malId".lowercase())
  }
}