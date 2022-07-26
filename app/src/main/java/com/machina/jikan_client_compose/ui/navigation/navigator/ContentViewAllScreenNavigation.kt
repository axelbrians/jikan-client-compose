package com.machina.jikan_client_compose.ui.navigation.navigator

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute

class ContentViewAllScreenNavigation(
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