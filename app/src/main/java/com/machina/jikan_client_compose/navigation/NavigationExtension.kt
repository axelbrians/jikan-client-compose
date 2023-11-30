package com.machina.jikan_client_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composable(
	destination: Destination,
	content: @Composable (NavBackStackEntry) -> Unit
) {
	composable(
		destinationParam = destination.destinationParam,
		content = content
	)
}

fun NavGraphBuilder.composable(
	destinationParam: DestinationParam,
	content: @Composable (NavBackStackEntry) -> Unit
) {
	composable(
		route = destinationParam.graphRoute,
		arguments = destinationParam.arguments,
		deepLinks = destinationParam.deepLinks,
		content = content
	)
}
