package com.machina.jikan_client_compose.presentation.home_screen

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.navigation.MainRoute
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsDestination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListDestination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNavArgs

class HomeScreenNavigator(
	private val navController: NavController
) {

	fun navigateToSearchScreen() {
		navController.navigate(MainRoute.Search.route)
	}

	fun navigateToContentViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
		val route = ContentViewAllListDestination.constructRoute(
			ContentViewAllListNavArgs(title, url, params)
		)
		navController.navigate(route)
	}

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
		val navArgs = ContentDetailsArgs(malId, contentType)
		val route = ContentDetailsDestination.createRoute(navArgs, 100)
		navController.navigate(route)
	}
}