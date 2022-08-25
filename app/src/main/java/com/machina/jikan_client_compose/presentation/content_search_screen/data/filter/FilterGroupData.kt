package com.machina.jikan_client_compose.presentation.content_search_screen.data.filter

import com.machina.jikan_client_compose.core.constant.AnimeConstant

data class FilterGroupData(
  val groupKey: String,
  val groupName: String,
  val isExpanded: Boolean,
  val type: FilterGroupType,
  val filterData: List<FilterItemData>
) {
  companion object {
    fun FilterGroupData.updateFilter(filter: List<FilterItemData>): FilterGroupData {
      return copy(filterData = filter)
    }

    val ContentRatingFilterGroup = FilterGroupData(
      groupKey = AnimeConstant.Rating,
      groupName = "Content Rating",
      isExpanded = true,
      type = FilterGroupType.Selectable,
      filterData = FilterItemData.getContentRatingList()
    )
  }
}


enum class FilterGroupType {
  Selectable,
  Checkable,
  Sortable
}