package com.machina.jikan_client_compose.presentation.content_search_screen.nav

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsDestination

class SearchScreenNavigator(
	private val navController: NavController
) {

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
		val contentDetailsArgs = ContentDetailsArgs(malId, contentType)
		navController.navigate(
			ContentDetailsDestination.constructRoute(contentDetailsArgs)
		)
	}

	fun navigateUp() {
		navController.navigateUp()
	}
}