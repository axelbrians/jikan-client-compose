package com.machina.jikan_client_compose.presentation.content_detail_screen.nav

import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.destinations.ContentDetailsNavDestination
import com.machina.jikan_client_compose.ui.navigation.content_view_all.ContentViewAllType
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class ContentDetailsScreenNavigator(
	private val navigator: DestinationsNavigator
) {

	fun navigateToContentViewAllScreen(
		type: ContentViewAllType,
		title: String
	) {
//    navigator.navigate("${MainNavigationRoute.CONTENT_VIEW_ALL_SCREEN}/${type.name}/${title}")
	}

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