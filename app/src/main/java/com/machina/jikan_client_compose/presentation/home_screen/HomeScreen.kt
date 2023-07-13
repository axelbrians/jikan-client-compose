package com.machina.jikan_client_compose.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.presentation.composable.MyDivider
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchFieldComponent
import com.machina.jikan_client_compose.presentation.home_screen.viewmodel.HomeViewModel
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


@InternalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
	navigator: HomeScreenNavigator,
	viewModel: HomeViewModel = hiltViewModel()
) {

	val animeAiringPopularState by viewModel.animeAiringPopular
	val animeScheduleState by viewModel.animeScheduleState
	val animeTopState by viewModel.animeTopState

	val snackbarHostState = remember { SnackbarHostState() }


	// Controlling snackbar on error. Only show one snackbar at a time with channel.
	// Channel create something like queue, so no snackbar will be showed when one is still showing.
	val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }


	LaunchedEffect(viewModel) {
		viewModel.getAnimeAiringPopular()
		viewModel.getTodayAnimeSchedule()
		viewModel.getTopAnimeList()
	}

	Scaffold(
		modifier = Modifier
			.fillMaxSize()
			.background(MyColor.DarkBlueBackground),
		snackbarHost = {
			SnackbarHost(hostState = snackbarHostState)
		}
	) {
		Column(modifier = Modifier
			.fillMaxWidth()
			.padding(it)) {
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
				animeAiringPopularState = animeAiringPopularState,
				animeScheduleState = animeScheduleState,
				animeTopState = animeTopState
			)
		}

		// Try to emmit error message to snackbarChannel if not have been handled before.
		with(animeTopState.error.getContentIfNotHandled()) {
			snackbarChannel.trySend(this)
		}

		// Side-effect to control how snackbar should showing
		LaunchedEffect(snackbarChannel) {
			snackbarChannel.receiveAsFlow().collect { error ->

				val result = if (error != null) {
					snackbarHostState.showSnackbar(
						message = error,
						actionLabel = "Dismiss",
						duration = SnackbarDuration.Long
					)
				} else {
					null
				}

				when (result) {
					SnackbarResult.ActionPerformed -> {
						/* action has been performed */

					}
					SnackbarResult.Dismissed -> {
						/* dismissed, no action needed */
					}
					else -> { }
				}
			}
		}
	}
}