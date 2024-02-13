package com.machina.jikan_client_compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

// todo: ab
//fun <T: Destination> NavGraphBuilder.composable(
//	destination: T,
//	content: @Composable T.(NavBackStackEntry) -> Unit
//) {
//	composable(
//		destinationParam = destination.destinationParam,
//		content = content
//	)
//}

fun <T> NavGraphBuilder.strongScopedComposable(
	destination: Destination,
	argumentParser: ArgumentParser<T>,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry, T) -> Unit
) {
	composable(
		destination = destination,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = { navBackStackEntry ->
			val param = argumentParser.parse(navBackStackEntry.arguments)

			content.invoke(
				this,
				navBackStackEntry,
				param
			)
		}
	)
}

fun NavGraphBuilder.scopedComposable(
	destination: Destination,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry, ArgumentEntry) -> Unit
) {
	composable(
		destination = destination,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = { navBackStackEntry ->
			content.invoke(
				this,
				navBackStackEntry,
				ArgumentEntry.create(destination.required, navBackStackEntry.arguments)
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

//private fun NavGraphBuilder.internalComposable(
//	destinationParam: DestinationParam,
//	enterTransition: EnterTransitionType? = null,
//	exitTransition: ExitTransitionType? = null,
//	popEnterTransition: EnterTransitionType? = enterTransition,
//	popExitTransition: ExitTransitionType? = exitTransition,
//	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit = { }
//) {
//	composable(
//		route = destinationParam.graphRoute,
//		arguments = destinationParam.arguments,
//		deepLinks = destinationParam.deepLinks,
//		enterTransition = enterTransition,
//		exitTransition = exitTransition,
//		popEnterTransition = popEnterTransition,
//		popExitTransition = popExitTransition,
//		content = content
//	)
//}
