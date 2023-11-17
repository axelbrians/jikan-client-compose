package com.machina.jikan_client_compose.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

object MainNavigationRoute {

	const val HOME_SCREEN = "home"
	const val CONTENT_SEARCH_SCREEN = "search"
	const val CONTENT_DETAILS_SCREEN = "home/anime_details"
	const val CONTENT_VIEW_ALL_SCREEN = "view_all_screen"
	const val ANIME_TOP_ALL_TIMES_SCREEN = "home_screen/anime_top/all_times"

}

abstract class Destination(
	val route: String,
	val arguments: List<NamedNavArgument> = emptyList(),
	val deepLinks: List<NavDeepLink> = emptyList()
)

sealed class MainRoute(
	route: String,
	arguments: List<NamedNavArgument> = emptyList(),
	deepLinks: List<NavDeepLink> = emptyList(),
): Destination(route, arguments, deepLinks) {
	object Home: MainRoute(route = "home")
	object Search: MainRoute(route = "search")
	object ContentDetails: MainRoute(route = "home/content_details")
}
