package com.machina.jikan_client_compose.presentation.content_small_view_all.nav

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class ContentSmallViewAllNavigator(
	private val navigator: DestinationsNavigator
) {

	fun navigateUp(): Boolean {
		return navigator.navigateUp()
	}
}