package com.machina.jikan_client_compose.presentation.content_search_screen.nav

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType

class SearchScreenNavigator(
	private val navController: NavController
) {

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
//		val direction = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
//		navigator.navigate(direction)
	}

	fun navigateUp() {
		navController.navigateUp()
	}
}