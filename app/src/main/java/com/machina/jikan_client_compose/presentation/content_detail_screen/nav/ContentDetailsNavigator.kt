package com.machina.jikan_client_compose.presentation.content_detail_screen.nav

import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_view_all_screen.nav.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.destinations.ContentDetailsNavDestination
import com.machina.jikan_client_compose.presentation.destinations.ContentSmallViewAllNavDestination
import com.machina.jikan_client_compose.presentation.destinations.ContentViewAllListNavDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class ContentDetailsScreenNavigator(
	private val navigator: DestinationsNavigator
) {

	fun navigateToContentViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
		val destination = ContentViewAllListNavDestination(
			ContentViewAllListNavArgs(title, url, params)
		)
		navigator.navigate(destination)
	}

	fun navigateToContentSmallViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
		val destination = ContentSmallViewAllNavDestination(
			ContentViewAllListNavArgs(title, url, params)
		)
		navigator.navigate(destination)
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