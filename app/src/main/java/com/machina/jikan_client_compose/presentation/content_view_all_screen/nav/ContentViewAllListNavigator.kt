package com.machina.jikan_client_compose.presentation.content_view_all_screen.nav

import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_detail_screen.nav.ContentDetailsNavArgs
import com.machina.jikan_client_compose.presentation.destinations.ContentDetailsNavDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class ContentViewAllScreenNavigator(
	private val navigator: DestinationsNavigator
) {
	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
		val direction = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
		navigator.navigate(direction)
	}

	fun navigateUp(): Boolean {
		return navigator.navigateUp()
	}
}