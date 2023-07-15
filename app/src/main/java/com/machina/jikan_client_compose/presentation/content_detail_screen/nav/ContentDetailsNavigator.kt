package com.machina.jikan_client_compose.presentation.content_detail_screen.nav

import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType

class ContentDetailsScreenNavigator(
	private val navController: NavController
) {

	fun navigateToContentViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
//		val destination = ContentViewAllListNavDestination(
//			ContentViewAllListNavArgs(title, url, params)
//		)
//		navigator.navigate(destination)
	}

	fun navigateToContentSmallViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
//		val destination = ContentSmallViewAllNavDestination(
//			ContentViewAllListNavArgs(title, url, params)
//		)
//		navigator.navigate(destination)
	}

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
//		val direction = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
//		navigator.navigate(direction)
	}

	fun navigateUp(): Boolean {
		return navController.navigateUp()
	}
}