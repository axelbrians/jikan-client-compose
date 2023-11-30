package com.machina.jikan_client_compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

object MainNavigationRoute {

	const val SEARCH = "search"
}

sealed class MainRoute(
	route: String,
	required: List<NamedNavArgument> = emptyList(),
	optional: List<NamedNavArgument> = emptyList(),
	deepLinks: List<NavDeepLink> = emptyList(),
): DestinationParam(route, required, optional, deepLinks) {
	object Search: MainRoute(route = MainNavigationRoute.SEARCH)
}
