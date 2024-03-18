package com.machina.jikan_client_compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composable(
	navigation: Navigation,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
	composable(
		route = navigation.destination.route,
		arguments = navigation.destination.arguments,
		deepLinks = navigation.destination.deepLinks,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = content
	)
}

fun <T>NavGraphBuilder.composable(
	navigation: NavigationWithArgument<T>,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry, T) -> Unit
) {
	composable(
		route = navigation.destination.route,
		arguments = navigation.destination.arguments,
		deepLinks = navigation.destination.deepLinks,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = { navBackStackEntry ->
			val param = navigation.parser.parse(navBackStackEntry.arguments)

			content.invoke(
				this,
				navBackStackEntry,
				param
			)
		}
	)
}
