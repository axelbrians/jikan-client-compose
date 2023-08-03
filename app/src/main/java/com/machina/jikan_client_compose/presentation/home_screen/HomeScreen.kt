package com.machina.jikan_client_compose.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.presentation.composable.MyDivider
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchFieldComponent
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.home_screen.viewmodel.HomeViewModel.HomeEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@InternalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
	navigator: HomeScreenNavigator,
	topState: State<StateListWrapper<AnimePortraitDataModel>>,
	homeSections: StateFlow<List<HomeSection>>,
	sendViewModelEvent: (HomeEvent) -> Unit,
	getHomeContent: () -> Unit,
	modifier: Modifier = Modifier
) {
	val snackbarHostState = remember { SnackbarHostState() }


	// Controlling snackbar on error. Only show one snackbar at a time with channel.
	// Channel create something like queue, so no snackbar will be showed when one is still showing.
	val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }


	LaunchedEffect(Unit) {
		getHomeContent.invoke()
		sendViewModelEvent(HomeEvent.GetAnimeTop)
	}

	Scaffold(modifier = modifier) {
		Column(modifier = Modifier.fillMaxWidth()) {
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

			HomeContentList(
				navigator = navigator,
				animeTopState = topState,
				homeSections = homeSections
			)
		}

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