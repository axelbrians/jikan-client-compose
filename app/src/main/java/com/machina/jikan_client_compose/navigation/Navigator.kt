package com.machina.jikan_client_compose.navigation

import androidx.navigation.NavController

interface Navigator {

	interface WithNavigateUp {
		fun navigateUp(): Boolean
	}
}


class NavigatorDelegate(
	private val navController: NavController
): Navigator.WithNavigateUp {
	override fun navigateUp(): Boolean {
		return navController.navigateUp()
	}
}