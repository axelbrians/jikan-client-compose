package com.machina.jikan_client_compose.presentation.content_view_all_screen.nav

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType

class ContentViewAllScreenNavigator(
	private val navController: NavController
) {
	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
//		val direction = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
//		navController.navigate(direction)
	}

	fun navigateUp(): Boolean {
		return navController.navigateUp()
	}
}