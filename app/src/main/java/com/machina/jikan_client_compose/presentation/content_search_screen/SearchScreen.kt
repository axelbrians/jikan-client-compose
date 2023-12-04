package com.machina.jikan_client_compose.presentation.content_search_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.extensions.isScrolledToTheEnd
import com.machina.jikan_client_compose.core.extensions.isScrollingUp
import com.machina.jikan_client_compose.navigation.Destination
import com.machina.jikan_client_compose.navigation.destinationParam
import com.machina.jikan_client_compose.presentation.composable.MyDivider
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.ContentSearchList
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchFieldComponent
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet.ExpandableFloatingButtonSearchScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet.FilterModalBottomSheet
import com.machina.jikan_client_compose.presentation.content_search_screen.data.SearchScreenViewModel
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.FilterAction
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.SearchEvent
import com.machina.jikan_client_compose.presentation.content_search_screen.nav.SearchScreenNavigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object SearchDestination: Destination(
	destinationParam = destinationParam {
		route = "search"
	}
) {
	fun constructRoute(): String {
		return super.createDestinationRoute()
	}
}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
	navigator: SearchScreenNavigator,
	viewModel: SearchScreenViewModel,
	modifier: Modifier = Modifier
) {
	val selectedType = rememberSaveable { mutableStateOf(ContentType.Anime) }
	val searchQuery = rememberSaveable { mutableStateOf("") }

	val listState = rememberLazyListState()
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

	val snackbarHostState = remember { SnackbarHostState() }
	val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }

	val contentSearchState by viewModel.contentSearchState
	val filterOptionState by viewModel.filterOptionState

	LaunchedEffect(key1 = viewModel.hashCode()) {
		focusRequester.requestFocus()
		viewModel.onFilterEvent(FilterAction.GetOption())
	}

	BackHandler(enabled = bottomSheetState.isVisible) {
		coroutineScope.launch {
			bottomSheetState.hide()
		}.invokeOnCompletion { shouldShowBottomSheet = false }
	}

	Box(modifier = modifier) {
		if (shouldShowBottomSheet) {
			FilterModalBottomSheet(
				selectedType = selectedType.value,
				searchQuery = searchQuery.value,
				filterOptionState = viewModel.filterOptionState,
				onFilterAction = viewModel::onFilterEvent,
				bottomSheetState = bottomSheetState,
				onHideBottomSheet = hideBottomSheet
			)
		}

		Column(modifier = Modifier.fillMaxWidth()) {
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

	//      ChipGroup(
	//        selectedType = selectedType.value,
	//        onSelectedChanged = { selectedType.value = ContentType.valueOf(it) }
	//      )

			ContentSearchList(
				listState = listState,
				state = contentSearchState,
				onItemClick = navigator::navigateToContentDetailsScreen
			)
		}

		ExpandableFloatingButtonSearchScreen(
			modifier = Modifier
				.padding(bottom = 16.dp, end = 16.dp)
				.align(Alignment.BottomEnd),
			isExpanded = listState.isScrollingUp(),
			onClick = {
				shouldShowBottomSheet = true
			}
		)
	}


	if (listState.isScrolledToTheEnd()) {
		viewModel.onSearchEvent(
			SearchEvent.SearchNextPage(selectedType.value, searchQuery.value)
		)
	}

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