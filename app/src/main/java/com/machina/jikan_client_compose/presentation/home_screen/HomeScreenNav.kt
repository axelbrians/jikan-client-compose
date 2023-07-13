package com.machina.jikan_client_compose.presentation.home_screen

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalCoroutinesApi::class, ExperimentalCoilApi::class)
@Composable
fun HomeScreenNav(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController,
	viewModel: HomeViewModel
) {
	OnDestinationChanged(
		systemUiController = systemUiController,
		color = MyColor.DarkBlueBackground,
		drawOverStatusBar = false,
		window = window,
	)

	HomeScreen(
		navigator = HomeScreenNavigator(navController),
		viewModel = viewModel
	)


//	HomeScreen(
//		navigator = HomeScreenNavigator(navController),
//		airingPopular = viewModel.airingPopular,
//		scheduleState = viewModel.animeScheduleState,
//		topState = viewModel.animeTopState,
//		sendViewModelEvent = viewModel::sendEvent
//	)
}


class HomeScreenNavigator(
	private val navController: NavController
) {

	fun navigateToSearchScreen() {
		navController.navigate(MainRoute.Search.route)
	}

	fun navigateToContentViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
//		val destination = ContentViewAllListNavDestination(
//			ContentViewAllListNavArgs(title, url, params)
//		)
//		navController.navigate(destination)
//    navController.navigate("${MainNavigationRoute.CONTENT_VIEW_ALL_SCREEN}/${type.name}/${title}")
	}

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
//		val destination = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
//		navController.navigate(destination)
	}
}