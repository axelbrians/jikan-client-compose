package com.machina.jikan_client_compose

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.machina.jikan_client_compose.presentation.content_detail_screen.addContentDetailsScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.addSearchScreen
import com.machina.jikan_client_compose.presentation.content_view_all_normal.addContentViewAllListScreen
import com.machina.jikan_client_compose.presentation.content_view_all_small.addSmallContentViewAllScreen
import com.machina.jikan_client_compose.presentation.home_screen.HomeNavigation
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
		startDestination = HomeNavigation.destination.route,
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

		addContentViewAllListScreen(
			systemUiController = systemUiController,
			window = window,
			navController = navController
		)

		addSmallContentViewAllScreen(
			systemUiController = systemUiController,
			window = window,
			navController = navController
		)

		addSearchScreen(
			systemUiController = systemUiController,
			window = window,
			navController = navController
		)
	}
}