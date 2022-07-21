package com.machina.jikan_client_compose.ui.navigation

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enum.ContentType

object MainNavigation {

  const val HOME_SCREEN = "/home_screen"
  const val CONTENT_SEARCH_SCREEN = "/search_screen"
  const val CONTENT_DETAILS_SCREEN = "/home_screen/anime_details"
  const val CONTENT_VIEW_ALL_SCREEN = "/view_all_screen"
  const val ANIME_TOP_ALL_TIMES_SCREEN = "/home_screen/anime_top/all_times"

  class HomeScreenNavigation(
    private val navController: NavController
  )

  class ContentViewAllScreenNavigation(
    private val navController: NavController
  ) {
    fun navigateToContentDetailsScreen(
      malId: Int,
      contentType: ContentType
    ) {
      navController.navigate("${CONTENT_DETAILS_SCREEN}/${contentType.name}/$malId".lowercase())
    }
  }
}