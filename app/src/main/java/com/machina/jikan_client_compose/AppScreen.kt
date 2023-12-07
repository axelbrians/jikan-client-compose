package com.machina.jikan_client_compose

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.machina.jikan_client_compose.navigation.composable
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsNav
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsDestination
import com.machina.jikan_client_compose.presentation.content_search_screen.SearchDestination
import com.machina.jikan_client_compose.presentation.content_search_screen.nav.SearchScreenNav
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNav
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListDestination
import com.machina.jikan_client_compose.presentation.content_view_all_small.ContentSmallViewAllNav
import com.machina.jikan_client_compose.presentation.content_view_all_small.ContentSmallViewAllDestination
import com.machina.jikan_client_compose.presentation.home_screen.HomeDestination
import com.machina.jikan_client_compose.presentation.home_screen.addHomeScreen
import timber.log.Timber

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

		composable(ContentDetailsDestination) { backStack ->
			// todo: ab
			// Pindahin parsing argument ke Custom Scope
//			this.navArgs
//			this.magicNumber
			val navArgs = ContentDetailsArgs.requireGet(
				bundle = backStack.arguments,
				key = ContentDetailsDestination.KEY_CONTENT_DETAIL_ARGS
			)
			val magicNumber = backStack.arguments?.getInt(ContentDetailsDestination.KEY_MAGIC_NUMBER)

			Timber.tag("puyo").d("args: $navArgs")
			Timber.tag("puyo").d("number: $magicNumber")

			ContentDetailsNav(
				systemUiController = systemUiController,
				window = window,
				navController = navController,
				navArgs = navArgs,
				viewModel = hiltViewModel()
			)
		}

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

		composable(SearchDestination) {
			SearchScreenNav(
				systemUiController = systemUiController,
				window = window,
				navController = navController
			)
		}

	}
}