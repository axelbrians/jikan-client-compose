package com.machina.jikan_client_compose.presentation.content_view_all_small

import androidx.navigation.NavController
import com.machina.jikan_client_compose.navigation.ArgumentParser
import com.machina.jikan_client_compose.navigation.NavigationWithArgument
import com.machina.jikan_client_compose.navigation.Navigator
import com.machina.jikan_client_compose.navigation.NavigatorDelegate
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllNavigation
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllNavigation.ContentViewAllArgs

object SmallContentViewAllNavigation: NavigationWithArgument<ContentViewAllArgs> {
	override val destination = destination {
		route = "home/content/view_all/small"
		addArgument(ContentViewAllNavigation.KEY_NAV_ARGS) {
			type = ContentViewAllArgs
		}
	}

	override val parser: ArgumentParser<ContentViewAllArgs> =
		ContentViewAllNavigation.parser

	fun constructRoute(navArgs: ContentViewAllArgs): String {
		return destination.createDestinationRoute(
			required = listOf(ContentViewAllNavigation.KEY_NAV_ARGS to navArgs)
		)
	}

	interface Navigator {
		fun navigateToSmallContentViewAll(
			title: String,
			url: String,
			params: Map<String, String> = mapOf()
		)
	}

	class NavigatorDelegate(private val navController: NavController): Navigator {
		override fun navigateToSmallContentViewAll(
			title: String,
			url: String,
			params: Map<String, String>
		) {
			val route = constructRoute(ContentViewAllArgs(title, url, params))
			navController.navigate(route)
		}
	}
}

class SmallContentViewAllNavigator(
	private val navController: NavController
): Navigator.WithNavigateUp by NavigatorDelegate(navController)
