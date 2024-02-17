package com.machina.jikan_client_compose.presentation.content_search_screen

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.navigation.Navigation
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsNavigation

object SearchNavigation: Navigation {

	override val destination = destination {
		route = "search"
	}

	fun constructRoute(): String {
		return destination.createDestinationRoute()
	}
}

class SearchScreenNavigator(
	private val navController: NavController
) {

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
		navController.navigate(
			ContentDetailsNavigation.constructRoute(malId, contentType)
		)
	}

	fun navigateUp() {
		navController.navigateUp()
	}
}
