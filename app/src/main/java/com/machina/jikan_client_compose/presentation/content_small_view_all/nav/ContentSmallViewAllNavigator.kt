package com.machina.jikan_client_compose.presentation.content_small_view_all.nav

import androidx.navigation.NavController

class ContentSmallViewAllNavigator(
	private val navController: NavController
) {

	fun navigateUp(): Boolean {
		return navController.navigateUp()
	}
}