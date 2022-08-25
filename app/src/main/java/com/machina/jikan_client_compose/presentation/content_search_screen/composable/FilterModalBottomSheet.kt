package com.machina.jikan_client_compose.presentation.content_search_screen.composable

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.item.FilterGroupHeader
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterItemData

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun FilterModalBottomSheet(
  modifier: Modifier = Modifier,
  filterData: FilterGroupData,
  onFilterChanged: (FilterGroupData) -> Unit,
  onFilterReset: () -> Unit,
  onFilterApplied: () -> Unit
) {
  val localDensity = LocalDensity.current
  val screenHeight = LocalConfiguration.current.screenHeightDp.dp / 3 * 2
  val scrollableHeight = remember { mutableStateOf(0.dp) }

  Column(
    modifier = if (scrollableHeight.value > screenHeight) {
      Modifier
        .height(screenHeight)
    } else {
      Modifier
    }
  ) {
    FilterModalBottomSheetPanButtonControl(
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
      FilterGroupHeader(
        data = filterData,
        onItemClicked = { index: Int ->
          val newFilterGroup = filterData.copy(filterData = calculateSelectable(filterData.filterData, index))
          onFilterChanged(newFilterGroup)
        }
      )

//      FilterGroupHeader(
//        filterData = ratingData,
//        filterGroup = FilterGroupHeader.Checkable,
//        onItemClicked = { index: Int ->
//          val temp = ratingData.value.toMutableList()
//          val prevFilter = temp[index]
//          temp[index] = prevFilter.copy(isChecked = !prevFilter.isChecked)
//          ratingData.value = temp
//        }
//      )
    }
  }
}

private fun calculateSelectable(data: List<FilterItemData>, index: Int): List<FilterItemData> {
  return data.mapIndexed { i, contentRating ->
    contentRating.copy(isChecked = index == i)
  }
}

private fun calculateCheckable(data: List<FilterItemData>, index: Int): List<FilterItemData> {
  return data.mapIndexed { i, filterItemData ->
    if (i == index) {
      filterItemData.copy(isChecked = !filterItemData.isChecked)
    } else {
      filterItemData
    }
  }
}