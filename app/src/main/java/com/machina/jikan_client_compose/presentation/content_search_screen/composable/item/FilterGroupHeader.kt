package com.machina.jikan_client_compose.presentation.content_search_screen.composable.item

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupType
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterItemData
import com.machina.jikan_client_compose.ui.theme.JikanTypography
import com.machina.jikan_client_compose.ui.theme.JikanTypography.bold
import com.machina.jikan_client_compose.ui.theme.JikanTypography.onDarkSurface
import com.machina.jikan_client_compose.ui.theme.MyColor

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
		modifier = modifier,
		transitionSpec = {
			fadeIn(animationSpec = tween(150, 150)) with
			  fadeOut(animationSpec = tween(150, 150)) using
			  SizeTransform(clip = true)
		},
		label = ""
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
					style = JikanTypography.JikanTextStyle.bodyLarge.onDarkSurface().bold(),
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