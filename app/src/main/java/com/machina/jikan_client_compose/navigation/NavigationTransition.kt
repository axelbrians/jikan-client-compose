package com.machina.jikan_client_compose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavBackStackEntry

typealias EnterTransitionType = (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)

typealias ExitTransitionType = (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)


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
