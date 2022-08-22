package com.machina.jikan_client_compose.presentation.content_search_screen.composable

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.item.FilterGroupHeader
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.rememberFilterContentRatingList

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ColumnScope.FilterModalBottomSheet(
  modifier: Modifier = Modifier
) {
  val localDensity = LocalDensity.current
  val screenHeight = LocalConfiguration.current.screenHeightDp.dp / 3 * 2
  val scrollableHeight = remember { mutableStateOf(0.dp) }
  val ratingData = rememberFilterContentRatingList()
  val selectRatingData = rememberFilterContentRatingList()

  Column(
    modifier = if (scrollableHeight.value > screenHeight) {
      Modifier
        .height(screenHeight)
    } else {
      Modifier
    }
  ) {
    FilterModalBottomSheetPanButtonControl()

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
        filterData = ratingData,
        filterGroup = FilterGroupHeader.Checkable,
        onItemClicked = { index: Int ->
          val temp = ratingData.value.toMutableList()
          val newRating = temp[index].copy(isChecked = !temp[index].isChecked)
          temp[index] = newRating
          ratingData.value = temp
        }
      )

      FilterGroupHeader(
        filterData = selectRatingData,
        filterGroup = FilterGroupHeader.Selectable,
        onItemClicked = { index: Int ->
          val temp = selectRatingData.value.mapIndexed { i, contentRating ->
            contentRating.copy(isChecked = index == i)
          }
          selectRatingData.value = temp
        }
      )
    }
  }
}