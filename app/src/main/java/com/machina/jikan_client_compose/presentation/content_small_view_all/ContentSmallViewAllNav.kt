package com.machina.jikan_client_compose.presentation.content_small_view_all

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.presentation.content_small_view_all.nav.ContentSmallViewAllNavigator
import com.machina.jikan_client_compose.presentation.content_view_all_screen.nav.ContentViewAllListNavArgs
import com.machina.jikan_client_compose.ui.theme.MyColor

@Composable
fun ContentSmallViewAllNav(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController,
	navArgs: ContentViewAllListNavArgs
) {
	OnDestinationChanged(
		systemUiController = systemUiController,
		color = MyColor.DarkGreyBackground,
		drawOverStatusBar = false,
		window = window,
	)

	ContentSmallViewAllScreen(
		navigator = ContentSmallViewAllNavigator(navController),
		viewModel = hiltViewModel(),
		gridSizeViewModel = hiltViewModel(),
		navArgs = navArgs
	)
}