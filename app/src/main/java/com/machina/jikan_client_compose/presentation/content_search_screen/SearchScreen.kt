package com.machina.jikan_client_compose.presentation.content_search_screen

import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.extensions.isScrolledToTheEnd
import com.machina.jikan_client_compose.core.extensions.isScrollingUp
import com.machina.jikan_client_compose.navigation.composable
import com.machina.jikan_client_compose.presentation.composable.MyDivider
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.ContentSearchList
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchFieldComponent
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet.ExpandableFloatingButtonSearchScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet.FilterModalBottomSheet
import com.machina.jikan_client_compose.presentation.content_search_screen.data.SearchScreenViewModel
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.FilterAction
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.SearchEvent
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

fun NavGraphBuilder.addSearchScreen(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController
) {
	composable(SearchNavigation) {
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
}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
	navigator: SearchScreenNavigator,
	viewModel: SearchScreenViewModel,
	modifier: Modifier = Modifier
) {
	val contentSearchState by viewModel.contentSearchState
	val filterOptionState = viewModel.filterOptionState

	val selectedType = rememberSaveable { mutableStateOf(ContentType.Anime) }
	val searchQuery = rememberSaveable { mutableStateOf("") }

	val listState = rememberLazyListState()
	val isScrolledToTheEnd by listState.isScrolledToTheEnd()

	if (isScrolledToTheEnd) {
		viewModel.onSearchEvent(
			SearchEvent.SearchNextPage(selectedType.value, searchQuery.value)
		)
	}

	val coroutineScope = rememberCoroutineScope()
	val focusRequester = remember { FocusRequester() }

	var shouldShowBottomSheet by remember { mutableStateOf(false) }
	val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	val hideBottomSheet = remember {{
		coroutineScope.launch {
			bottomSheetState.hide()
		}.invokeOnCompletion { shouldShowBottomSheet = false }
		Unit
	}}

	LaunchedEffect(viewModel.hashCode()) {
		focusRequester.requestFocus()
		viewModel.onFilterEvent(FilterAction.GetOption())
	}

	BackHandler(enabled = bottomSheetState.isVisible) {
		coroutineScope.launch {
			bottomSheetState.hide()
		}.invokeOnCompletion { shouldShowBottomSheet = false }
	}

	Scaffold(
		modifier = modifier,
		topBar = {
			SearchFieldComponent(
				value = searchQuery.value,
				modifier = Modifier.padding(12.dp),
				focusRequester = focusRequester,
				onValueChanged = {
					searchQuery.value = it
					viewModel.onSearchEvent(
						SearchEvent.SearchFirstPage(selectedType.value, searchQuery.value)
					)
				},
				onValueCleared = { searchQuery.value = "" },
			)

			MyDivider.Horizontal.DarkGreyBackground()
		},
		floatingActionButton = {
			ExpandableFloatingButtonSearchScreen(
				modifier = Modifier
					.padding(bottom = 16.dp, end = 16.dp),
				isExpanded = listState.isScrollingUp(),
				onClick = {
					shouldShowBottomSheet = true
				}
			)
		},
		floatingActionButtonPosition = FabPosition.End
	) {
		Box(modifier = Modifier.padding(it)) {
			//      ChipGroup(
			//        selectedType = selectedType.value,
			//        onSelectedChanged = { selectedType.value = ContentType.valueOf(it) }
			//      )

			ContentSearchList(
				listState = listState,
				state = contentSearchState,
				onItemClick = navigator::navigateToContentDetailsScreen,
				modifier = Modifier.fillMaxSize()
			)

			if (shouldShowBottomSheet) {
				FilterModalBottomSheet(
					selectedType = selectedType.value,
					searchQuery = searchQuery.value,
					filterOptionState = filterOptionState,
					onFilterAction = viewModel::onFilterEvent,
					bottomSheetState = bottomSheetState,
					onHideBottomSheet = hideBottomSheet
				)
			}
		}
	}



	val snackbarHostState = remember { SnackbarHostState() }
	val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }

	// Try to emmit error message to snackbarChannel if not have been handled before.
	with(contentSearchState.error.getContentIfNotHandled()) {
		snackbarChannel.trySend(this)
	}

	// Side-effect to control how snackbar should showing
	LaunchedEffect(snackbarChannel) {
		snackbarChannel.receiveAsFlow().collect { error ->
			val result = if (error != null) {
				snackbarHostState.showSnackbar(
					message = error,
					actionLabel = "Dismiss"
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


