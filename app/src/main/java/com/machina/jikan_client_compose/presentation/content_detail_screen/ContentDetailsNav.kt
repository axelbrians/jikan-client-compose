package com.machina.jikan_client_compose.presentation.content_detail_screen

import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListDestination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_small.ContentSmallViewAllDestination
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Serializable
data class ContentDetailsArgs(
	val malId: Int,
	val contentType: ContentType
) {
	override fun toString(): String {
		return serializeAsValue(this)
	}

	companion object : SerializableNavType<ContentDetailsArgs>(serializer())

}

class ContentDetailsScreenNavigator(
	private val navController: NavController
) {

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

	fun navigateToContentSmallViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
		val route = ContentSmallViewAllDestination.constructRoute(
			ContentViewAllListNavArgs(title, url, params)
		)
		navController.navigate(route)
	}

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
		val route = ContentDetailsDestination.constructRoute(
			ContentDetailsArgs(malId, contentType), 10
		)
		navController.navigate(
			route,
			navOptions = navOptions { this.launchSingleTop = true }
		)
	}

	fun navigateUp(): Boolean {
		return navController.navigateUp()
	}
}