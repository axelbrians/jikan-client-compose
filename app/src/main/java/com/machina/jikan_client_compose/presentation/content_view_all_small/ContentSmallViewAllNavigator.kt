package com.machina.jikan_client_compose.presentation.content_view_all_small

import androidx.navigation.NavController

class ContentSmallViewAllNavigator(
	private val navController: NavController
) {

	fun navigateUp(): Boolean {
		return navController.navigateUp()
	}
}