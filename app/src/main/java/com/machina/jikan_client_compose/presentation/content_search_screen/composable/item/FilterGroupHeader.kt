package com.machina.jikan_client_compose.presentation.content_search_screen.composable.item

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupType
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterItemData
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.bold
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FilterGroupHeader(
  modifier: Modifier = Modifier,
  filterGroupData: FilterGroupData,
  onItemClicked: (FilterGroupData) -> Unit
) {
  var isExpanded by remember { mutableStateOf(false) }
  val groupKey = filterGroupData.groupKey
  val filterData = filterGroupData.filterData

  AnimatedContent(
    targetState = isExpanded,
    transitionSpec = {
      fadeIn(animationSpec = tween(150, 150)) with
        fadeOut(animationSpec = tween(150, 150)) using
        SizeTransform(clip = true)
    }
  ) { targetExpanded ->
    Column {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { isExpanded = !isExpanded }
          .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = filterGroupData.groupName,
          style = Type.Typography.subtitle1.onDarkSurface().bold(),
          modifier = Modifier
            .weight(1f)
            .padding(vertical = 12.dp)
        )
        Icon(
          imageVector = if (isExpanded) {
            Icons.Default.KeyboardArrowUp
          } else {
            Icons.Default.KeyboardArrowDown
          },
          contentDescription = "Toggle expand",
          tint = MyColor.Grey
        )
      }
      if (targetExpanded) {
        filterData.mapIndexed { index: Int, data: FilterItemData ->
          when (filterGroupData.type) {
            FilterGroupType.Checkable -> {
              FilterCheckable(
                text = data.name,
                isChecked = data.isChecked,
                onCheck = {
                  onItemClicked(calculateCheckable(filterGroupData, index))
                }
              )
            }
            FilterGroupType.Selectable -> {
              FilterSelectable(
                text = data.name,
                isSelected = data.isChecked,
                onSelect = {
                  onItemClicked(calculateSelectable(filterGroupData, index))
                }
              )
            }
            FilterGroupType.Switchable -> {

            }
          }
        }
      }
    }
  }
}

private fun calculateSelectable(data: FilterGroupData, index: Int): FilterGroupData {
  val newFilter = data.filterData.mapIndexed { i, filterItem ->
    filterItem.copy(isChecked = index == i)
  }

  return data.copy(filterData = newFilter)
}

private fun calculateCheckable(data: FilterGroupData, index: Int): FilterGroupData {
  val newFilter = data.filterData.mapIndexed { i, filterItem ->
    if (i == index) {
      filterItem.copy(isChecked = !filterItem.isChecked)
    } else {
      filterItem
    }
  }

  return data.copy(filterData = newFilter)
}