package com.machina.jikan_client_compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

// Limit the number of argument can be parsed by this strongScopedComposable

@Suppress("UNCHECKED_CAST")
fun NavGraphBuilder.strongScopedComposable(
	destination: Navigation,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
	composable(
		route = destination.destination.route,
		arguments = destination.destination.arguments,
		deepLinks = destination.destination.deepLinks,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = content
	)
}

fun <T: Argument>NavGraphBuilder.strongScopedComposable(
	destination: NavigationWithArgument<T>,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry, T) -> Unit
) {
	composable(
		route = destination.destination.route,
		arguments = destination.destination.arguments,
		deepLinks = destination.destination.deepLinks,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = { navBackStackEntry ->
			val param = destination.parser.parse(navBackStackEntry.arguments)

			content.invoke(
				this,
				navBackStackEntry,
				param
			)
		}
	)
}

fun NavGraphBuilder.composable(
	destination: Destination,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
	composable(
		route = destination.route,
		arguments = destination.arguments,
		deepLinks = destination.deepLinks,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = content
	)
}
