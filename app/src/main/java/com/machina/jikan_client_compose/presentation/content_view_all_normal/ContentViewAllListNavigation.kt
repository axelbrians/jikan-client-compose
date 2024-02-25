package com.machina.jikan_client_compose.presentation.content_view_all_normal

import android.os.Bundle
import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.navigation.Argument
import com.machina.jikan_client_compose.navigation.ArgumentParser
import com.machina.jikan_client_compose.navigation.NavigationWithArgument
import com.machina.jikan_client_compose.navigation.Navigator
import com.machina.jikan_client_compose.navigation.NavigatorDelegate
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.navigation.destination
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

object ContentViewAllNavigation :
	NavigationWithArgument<ContentViewAllNavigation.ContentViewAllArgs> {

	const val KEY_NAV_ARGS = "contentViewAllListNavArgs"

	override val destination = destination {
		route = "home/content/view_all/normal"
		addArgument(KEY_NAV_ARGS) {
			type = ContentViewAllArgs
		}
	}

	override val parser = object : ArgumentParser<ContentViewAllArgs> {
		override fun parse(bundle: Bundle?): ContentViewAllArgs {
			return ContentViewAllArgs.requireGet(
				bundle = bundle,
				key = KEY_NAV_ARGS
			)
		}
	}

	fun constructRoute(navArgs: ContentViewAllArgs): String {
		return destination.createDestinationRoute(
			required = listOf(KEY_NAV_ARGS to navArgs)
		)
	}

	@Serializable
	data class ContentViewAllArgs(
		val title: String,
		val url: String,
		val params: Map<String, String> = mapOf()
	) : Argument {

		override fun serialize(): String {
			return serializeAsValue(this)
		}

		companion object : SerializableNavType<ContentViewAllArgs>(serializer())
	}

	interface Navigator {
		fun navigateToContentViewAllScreen(
			title: String,
			url: String,
			params: Map<String, String> = mapOf()
		)
	}

	class NavigatorDelegate(private val navController: NavController): Navigator {
		override fun navigateToContentViewAllScreen(
			title: String,
			url: String,
			params: Map<String, String>
		) {
			val route = constructRoute(ContentViewAllArgs(title, url, params))
			navController.navigate(route)
		}
	}
}

class ContentViewAllScreenNavigator(
	private val navController: NavController
): Navigator.WithNavigateUp by NavigatorDelegate(navController) {
	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
//		val direction = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
//		navController.navigate(direction)
	}
}
