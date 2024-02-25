package com.machina.jikan_client_compose.presentation.home_screen

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.navigation.Navigation
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsNavigation
import com.machina.jikan_client_compose.presentation.content_search_screen.SearchNavigation
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllNavigation
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllNavigation.ContentViewAllArgs

object HomeNavigation: Navigation {
	override val destination = destination {
		route = "home"
	}
}

class HomeScreenNavigator(private val navController: NavController) {

	fun navigateToSearchScreen() {
		navController.navigate(SearchNavigation.constructRoute())
	}

	fun navigateToContentViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
		val route = ContentViewAllNavigation.constructRoute(
			ContentViewAllArgs(title, url, params)
		)
		navController.navigate(route)
	}

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
		val route = ContentDetailsNavigation.constructRoute(malId, contentType)
		navController.navigate(route)
	}
}