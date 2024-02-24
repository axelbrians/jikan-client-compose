package com.machina.jikan_client_compose.presentation.content_view_all_normal

import android.os.Bundle
import androidx.navigation.NavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.navigation.Argument
import com.machina.jikan_client_compose.navigation.ArgumentParser
import com.machina.jikan_client_compose.navigation.NavigationWithArgument
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.navigation.destination
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

object ContentViewAllListDestination :
	NavigationWithArgument<ContentViewAllListDestination.ContentViewAllListNavArgs> {

	const val KEY_NAV_ARGS = "contentViewAllListNavArgs"

	override val destination = destination {
		route = "home/content/view_all/normal"
		addNav(KEY_NAV_ARGS) {
			type = ContentViewAllListNavArgs
		}
	}

	override val parser = object : ArgumentParser<ContentViewAllListNavArgs> {
		override fun parse(bundle: Bundle?): ContentViewAllListNavArgs {
			return ContentViewAllListNavArgs.requireGet(
				bundle = bundle,
				key = KEY_NAV_ARGS
			)
		}
	}

	fun constructRoute(navArgs: ContentViewAllListNavArgs): String {
		return destination.createDestinationRoute(
			required = listOf(KEY_NAV_ARGS to navArgs)
		)
	}

	@Serializable
	data class ContentViewAllListNavArgs(
		val title: String,
		val url: String,
		val params: Map<String, String> = mapOf()
	) : Argument {

		override fun serialize(): String {
			return serializeAsValue(this)
		}

		companion object : SerializableNavType<ContentViewAllListNavArgs>(serializer())
	}
}

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
