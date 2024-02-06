package com.machina.jikan_client_compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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

typealias EnterTransitionType = (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)

typealias ExitTransitionType = (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)


fun NavGraphBuilder.composable(
	destination: Destination,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
	composable(
		destinationParam = destination.destinationParam,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = content
	)
}

fun NavGraphBuilder.composable(
	destinationParam: DestinationParam,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit = { }
) {
	composable(
		route = destinationParam.graphRoute,
		arguments = destinationParam.arguments,
		deepLinks = destinationParam.deepLinks,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = content
	)
}

sealed interface ScaleTransitionDirection {
	object Inwards: ScaleTransitionDirection
	object Outwards: ScaleTransitionDirection
}

fun scaleIntoContainer(
	direction: ScaleTransitionDirection = ScaleTransitionDirection.Inwards,
	initialScale: Float = if (direction == ScaleTransitionDirection.Outwards) 0.9f else 1.1f
): EnterTransition {
	return scaleIn(
		animationSpec = tween(220, delayMillis = 90),
		initialScale = initialScale
	) + fadeIn(animationSpec = tween(220, delayMillis = 90))
}

fun scaleOutOfContainer(
	direction: ScaleTransitionDirection = ScaleTransitionDirection.Inwards,
	targetScale: Float = if (direction == ScaleTransitionDirection.Outwards) 0.9f else 1.1f
): ExitTransition {
	return scaleOut(
		animationSpec = tween(
			durationMillis = 220,
			delayMillis = 90
		),
		targetScale = targetScale
	) + fadeOut(tween(delayMillis = 90))
}
