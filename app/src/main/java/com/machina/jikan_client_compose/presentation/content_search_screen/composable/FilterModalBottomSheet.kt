package com.machina.jikan_client_compose.presentation.content_search_screen.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.item.FilterGroupHeader
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData

@Composable
fun ColumnScope.FilterModalBottomSheet(
  modifier: Modifier = Modifier,
  mapFilter: Map<String, FilterGroupData>,
  onFilterChanged: (FilterGroupData) -> Unit,
  onFilterReset: () -> Unit,
  onFilterApplied: () -> Unit
) {
  val localDensity = LocalDensity.current
  val screenHeight = LocalConfiguration.current.screenHeightDp.dp / 3 * 2
  val scrollableHeight = remember { mutableStateOf(0.dp) }

//  Column(
//    modifier = if (scrollableHeight.value > screenHeight) {
//      modifier
//        .height(screenHeight)
//    } else {
//      modifier
//    }
//  ) {
    FilterModalBottomSheetPanButtonControl(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      onReset = onFilterReset,
      onApply = onFilterApplied
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .onGloballyPositioned {
          with(localDensity) {
            scrollableHeight.value = it.size.height.toDp()
          }
        }
    ) {
      for ((_, filterGroup) in mapFilter) {
        FilterGroupHeader(
          filterGroupData = filterGroup,
          onItemClicked = { onFilterChanged(it) }
        )
      }
    }
//  }
}