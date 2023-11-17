package com.machina.jikan_client_compose.presentation.content_detail_screen

import android.os.Parcelable
import android.view.Window
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalAnimationApi::class, ExperimentalCoilApi::class)
@Composable
fun ContentDetailsNav(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController,
	navArgs: ContentDetailsNavArgs,
	viewModel: ContentDetailsViewModel = hiltViewModel()
) {
	OnDestinationChanged(
		systemUiController = systemUiController,
		color = MyColor.Transparent,
		drawOverStatusBar = true,
		window = window,
	)
	Box(modifier = Modifier.fillMaxSize()) {
		ContentDetailsScreen(
			navigator = ContentDetailsScreenNavigator(navController),
			viewModel = viewModel,
			navArgs = navArgs
		)
	}
}

@Parcelize
data class ContentDetailsNavArgs(
	val malId: Int,
	val contentType: ContentType
) : Parcelable

class ContentDetailsScreenNavigator(
	private val navController: NavController
) {

	fun navigateToContentViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
//		val destination = ContentViewAllListNavDestination(
//			ContentViewAllListNavArgs(title, url, params)
//		)
//		navigator.navigate(destination)
	}

	fun navigateToContentSmallViewAllScreen(
		title: String,
		url: String,
		params: Map<String, String> = mapOf()
	) {
//		val destination = ContentSmallViewAllNavDestination(
//			ContentViewAllListNavArgs(title, url, params)
//		)
//		navigator.navigate(destination)
	}

	fun navigateToContentDetailsScreen(
		malId: Int,
		contentType: ContentType
	) {
//		val direction = ContentDetailsNavDestination(ContentDetailsNavArgs(malId, contentType))
//		navigator.navigate(direction)
	}

	fun navigateUp(): Boolean {
		return navController.navigateUp()
	}
}