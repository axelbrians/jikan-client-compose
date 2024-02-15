package com.machina.jikan_client_compose.presentation.content_detail_screen

import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListDestination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_small.ContentSmallViewAllDestination
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

object ContentDetailsDestination {
	const val KEY_CONTENT_DETAIL_ARGS = "contentDetailArgs"
	const val KEY_MAGIC_NUMBER = "magicNumber"

	val destination = destination {
		// todo: ab
		// DSL karena route wajib ada, harus jadi required
		// Define route nya kalo bisa ngikutin KTOR, pake path based. bisa apped static path atau
		// dynamic (dynamic ini bakal di replace sama argument) static adalah route aslinya.
		// route = "home/content/{}/details"
		route = "home/content/details"
//		argumentParser = ContentDetailArgumentParser()
		requiredNav(
			navArgument(KEY_CONTENT_DETAIL_ARGS) {
				type = ContentDetailsArgs
			}
		)
		requiredNav(
			navArgument(KEY_MAGIC_NUMBER) {
				type = NavType.IntType
			}
		)
//		optionalNav(
//			navArgument("optional") {
//				type = NavType.IntType
//			}
//		)
	}

	fun constructRoute(args: ContentDetailsArgs, number: Int = 0): String {
		return destination.createDestinationRoute(
			KEY_CONTENT_DETAIL_ARGS to args,
			KEY_MAGIC_NUMBER to number
		)
	}
}

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
