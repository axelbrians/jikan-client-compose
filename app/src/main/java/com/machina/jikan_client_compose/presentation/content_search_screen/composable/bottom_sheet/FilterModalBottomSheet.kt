package com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.composable.CircularLoading
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.item.FilterGroupHeader
import com.machina.jikan_client_compose.presentation.content_search_screen.data.FilterOptionState
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.FilterAction
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalBottomSheet(
	searchQuery: String,
	selectedType: ContentType,
	filterOptionState: State<FilterOptionState>,
	bottomSheetState: SheetState,
	onHideBottomSheet: () -> Unit,
	onFilterAction: (FilterAction) -> Unit,
	modifier: Modifier = Modifier,
) {
	ModalBottomSheet(
		modifier = modifier,
		sheetState = bottomSheetState,
		onDismissRequest = onHideBottomSheet,
		scrimColor = Color.Black.copy(alpha = 0.6f),
		containerColor = MyColor.DarkBlueBackground,
		contentColor = MyColor.Yellow500,
		shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp),
		windowInsets = WindowInsets.systemBars,
		dragHandle = {
			Box(
				modifier = Modifier
					.padding(top = 16.dp)
					.width(48.dp)
					.height(6.dp)
					.clip(MyShape.RoundedAllPercent50)
					.background(MyColor.Grey)
			)
		},
	) {
		FilterModalBottomSheetButtonControl(
			modifier = Modifier.align(Alignment.CenterHorizontally),
			onReset = {
				onFilterAction(FilterAction.Reset(selectedType, searchQuery))
				onHideBottomSheet()
			},
			onApply = {
				onFilterAction(FilterAction.Apply(selectedType, searchQuery))
				onHideBottomSheet()
			}
		)

		Column(
			modifier = Modifier
				.fillMaxWidth()
				.verticalScroll(rememberScrollState())
		) {
			with(filterOptionState.value) {
				if (isLoading) {
					CircularLoading(
						modifier = Modifier
							.size(48.dp)
							.padding(12.dp)
							.align(Alignment.CenterHorizontally)
					)
				}

				filterOptionState.value.data.forEach { (_, filterGroup) ->
					FilterGroupHeader(
						filterGroupData = filterGroup,
						onItemClicked = { data: FilterGroupData ->
							onFilterAction(FilterAction.Change(data))
						}
					)
				}
			}
		}
	}
}