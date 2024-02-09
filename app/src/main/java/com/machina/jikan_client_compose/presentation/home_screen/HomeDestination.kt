package com.machina.jikan_client_compose.presentation.home_screen

import android.view.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.navigation.ScaleTransitionDirection
import com.machina.jikan_client_compose.navigation.composable
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.navigation.scaleIntoContainer
import com.machina.jikan_client_compose.navigation.scaleOutOfContainer
import com.machina.jikan_client_compose.presentation.composable.MyDivider
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchFieldComponent
import com.machina.jikan_client_compose.presentation.home_screen.viewmodel.HomeViewModel
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

object HomeDestination {
	val destination = destination {
		route = "home"
	}
}

@OptIn(
	InternalCoroutinesApi::class,
	ExperimentalCoilApi::class
)
fun NavGraphBuilder.addHomeScreen(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController
) {
	composable(
		destination = HomeDestination.destination,
		enterTransition = {
			scaleIntoContainer(ScaleTransitionDirection.Inwards)
		},
		exitTransition = {
			scaleOutOfContainer(ScaleTransitionDirection.Outwards)
		},
		popEnterTransition = {
			scaleIntoContainer(ScaleTransitionDirection.Outwards)
		},
		popExitTransition = {
			scaleOutOfContainer(ScaleTransitionDirection.Inwards)
		}
	) {
		val viewModel: HomeViewModel = hiltViewModel()

		LaunchedEffect(viewModel.hashCode()) {
			viewModel.getHomeSections()
		}

		OnDestinationChanged(
			systemUiController = systemUiController,
			color = MyColor.DarkBlueBackground,
			drawOverStatusBar = false,
			window = window,
		)

		HomeScreen(
			navigator = HomeScreenNavigator(navController),
			homeSections = viewModel.homeState,
			getHomeSections = viewModel::getHomeSections,
			modifier = Modifier.fillMaxSize()
		)
	}
}

@InternalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
	navigator: HomeScreenNavigator,
	homeSections: StateFlow<HomeViewModel.HomeState>,
	getHomeSections: () -> Unit,
	modifier: Modifier = Modifier
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			SearchFieldComponent(
				value = "",
				modifier = Modifier
					.fillMaxWidth()
					.padding(12.dp)
					.clip(RoundedCornerShape(8.dp))
					.clickable(
						onClick = { navigator.navigateToSearchScreen() }
					),
				isEnabled = false,
				onValueChanged = { },
				onValueCleared = { }
			)
			MyDivider.Horizontal.DarkGreyBackground()
		}
	) {
		HomeContentList(
			navigator = navigator,
			homeSections = homeSections,
			getHomeSections = getHomeSections,
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
		)
//		val snackbarHostState = remember { SnackbarHostState() }


		// Controlling snackbar on error. Only show one snackbar at a time with channel.
		// Channel create something like queue, so no snackbar will be showed when one is still showing.
//		val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }

		// Try to emmit error message to snackbarChannel if not have been handled before.
//		with(animeTopState.error.getContentIfNotHandled()) {
//			snackbarChannel.trySend(this)
//		}

		// Side-effect to control how snackbar should showing
//		LaunchedEffect(snackbarChannel) {
//			snackbarChannel.receiveAsFlow().collect { error ->
//
//				val result = if (error != null) {
//					snackbarHostState.showSnackbar(
//						message = error,
//						actionLabel = "Dismiss",
//						duration = SnackbarDuration.Long
//					)
//				} else {
//					null
//				}
//
//				when (result) {
//					SnackbarResult.ActionPerformed -> {
//						/* action has been performed */
//					}
//					SnackbarResult.Dismissed -> {
//						/* dismissed, no action needed */
//					}
//				}
//			}
//		}
	}
}
