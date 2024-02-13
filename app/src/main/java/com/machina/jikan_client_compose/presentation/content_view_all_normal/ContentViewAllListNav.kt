package com.machina.jikan_client_compose.presentation.content_view_all_normal

import android.view.Window
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.navigation.composable
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

object ContentViewAllListDestination {
	val destination = destination {
		route = "home/content/view_all/normal"
		requiredNav(
			navArgument(KEY_NAV_ARGS) {
				type = ContentViewAllListNavArgs
			}
		)
	}

	const val KEY_NAV_ARGS = "contentViewAllListNavArgs"

	fun constructRoute(navArgs: ContentViewAllListNavArgs): String {
		return destination.createDestinationRoute(KEY_NAV_ARGS to navArgs)
	}
}

@Serializable
data class ContentViewAllListNavArgs(
	val title: String,
	val url: String,
	val params: Map<String, String> = mapOf()
) {

	override fun toString(): String {
		return serializeAsValue(this)
	}

	companion object : SerializableNavType<ContentViewAllListNavArgs>(serializer())
}

fun NavGraphBuilder.addContentViewAllDestination(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController
) {
	composable(
		destination = ContentViewAllListDestination.destination,
		enterTransition = {
			this.slideIntoContainer(
				towards = AnimatedContentTransitionScope.SlideDirection.Start,
				animationSpec = tween(durationMillis = 500)
			)
		},
		exitTransition = {
			this.slideOutOfContainer(
				towards = AnimatedContentTransitionScope.SlideDirection.End,
				animationSpec = tween(durationMillis = 500)
			)
		}
	) { backStack ->
		val navArgs = ContentViewAllListNavArgs.requireGet(
			bundle = backStack.arguments,
			key = ContentViewAllListDestination.KEY_NAV_ARGS
		)

		OnDestinationChanged(
			systemUiController = systemUiController,
			color = MyColor.DarkGreyBackground,
			drawOverStatusBar = false,
			window = window
		)

		ContentViewAllListScreen(
			navigator = ContentViewAllScreenNavigator(navController),
			viewModel = hiltViewModel(),
			navArgs = navArgs
		)
	}
}
