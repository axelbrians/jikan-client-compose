package com.machina.jikan_client_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.machina.jikan_client_compose.ui.navigation.Destination

fun NavGraphBuilder.composable(
	destination: Destination,
	content: @Composable (NavBackStackEntry) -> Unit
) {
	composable(
		route = destination.route,
		arguments = destination.arguments,
		deepLinks = destination.deepLinks
	) {
		content(it)
	}
}