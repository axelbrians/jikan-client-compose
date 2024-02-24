package com.machina.jikan_client_compose.presentation.content_view_all_small

import androidx.navigation.NavController
import com.machina.jikan_client_compose.navigation.ArgumentParser
import com.machina.jikan_client_compose.navigation.NavigationWithArgument
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListDestination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListDestination.ContentViewAllListNavArgs

object SmallContentViewAllNavigation: NavigationWithArgument<ContentViewAllListNavArgs> {
	override val destination = destination {
		route = "home/content/view_all/small"
		addNav(ContentViewAllListDestination.KEY_NAV_ARGS) {
			type = ContentViewAllListNavArgs
		}
	}

	override val parser: ArgumentParser<ContentViewAllListNavArgs> =
		ContentViewAllListDestination.parser

	fun constructRoute(navArgs: ContentViewAllListNavArgs): String {
		return destination.createDestinationRoute(
			required = listOf(ContentViewAllListDestination.KEY_NAV_ARGS to navArgs)
		)
	}
}

class SmallContentViewAllNavigator(
	private val navController: NavController
) {

	fun navigateUp(): Boolean {
		return navController.navigateUp()
	}
}
