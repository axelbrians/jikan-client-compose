package com.machina.jikan_client_compose.presentation.content_search_screen

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.presentation.content_search_screen.nav.SearchScreenNavigator
import com.machina.jikan_client_compose.ui.theme.MyColor


@Composable
fun SearchNav(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController
) {
	OnDestinationChanged(
		systemUiController = systemUiController,
		color = MyColor.DarkBlueBackground,
		drawOverStatusBar = false,
		window = window,
	)

	SearchScreen(
		navigator = SearchScreenNavigator(navController),
		viewModel = hiltViewModel()
	)
}