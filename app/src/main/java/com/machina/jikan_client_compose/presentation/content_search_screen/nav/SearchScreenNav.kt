package com.machina.jikan_client_compose.presentation.content_search_screen.nav

import android.view.Window
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.presentation.content_search_screen.SearchScreen
import com.machina.jikan_client_compose.ui.theme.MyColor


@Composable
fun SearchScreenNav(
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
		viewModel = hiltViewModel(),
		modifier = Modifier.fillMaxSize()
	)
}