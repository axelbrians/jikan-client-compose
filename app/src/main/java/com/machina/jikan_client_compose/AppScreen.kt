package com.machina.jikan_client_compose

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.machina.jikan_client_compose.navigation.composable
import com.machina.jikan_client_compose.presentation.content_detail_screen.addContentDetailsScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.addSearchScreen
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListDestination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNav
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_small.ContentSmallViewAllDestination
import com.machina.jikan_client_compose.presentation.content_view_all_small.ContentSmallViewAllNav
import com.machina.jikan_client_compose.presentation.home_screen.HomeDestination
import com.machina.jikan_client_compose.presentation.home_screen.addHomeScreen

@OptIn(
	coil.annotation.ExperimentalCoilApi::class,
	androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun AppScreen(
	window: Window,
	modifier: Modifier = Modifier
) {
	val systemUiController = rememberSystemUiController()
	val navController = rememberNavController()

	NavHost(
		navController = navController,
		startDestination = HomeDestination.destinationParam.graphRoute,
		modifier = modifier
	) {
		addHomeScreen(
			systemUiController = systemUiController,
			window = window,
			navController = navController
		)

		addContentDetailsScreen(
			systemUiController = systemUiController,
			window = window,
			navController = navController
		)

		composable(ContentViewAllListDestination) { backStack ->
			val navArgs = ContentViewAllListNavArgs.requireGet(
				bundle = backStack.arguments,
				key = ContentViewAllListDestination.KEY_NAV_ARGS
			)

			ContentViewAllListNav(
				systemUiController = systemUiController,
				window = window,
				navController = navController,
				navArgs = navArgs
			)
		}

		composable(ContentSmallViewAllDestination) { backStack ->
			val navArgs = ContentViewAllListNavArgs.requireGet(
				bundle = backStack.arguments,
				key = ContentSmallViewAllDestination.KEY_NAV_ARGS
			)

			ContentSmallViewAllNav(
				systemUiController = systemUiController,
				window = window,
				navController = navController,
				navArgs = navArgs
			)
		}

		addSearchScreen(
			systemUiController = systemUiController,
			window = window,
			navController = navController
		)
	}
}