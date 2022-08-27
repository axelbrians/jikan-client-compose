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
      groupKey = AnimeConstant.RatingKey,
      groupName = AnimeConstant.ContentRating,
      isExpanded = false,
      type = FilterGroupType.Selectable,
      filterData = FilterItemData.getContentRatingList()
    )

    val StatusFilterGroup = FilterGroupData(
      groupKey = AnimeConstant.StatusKey,
      groupName = AnimeConstant.Status,
      isExpanded = false,
      type = FilterGroupType.Selectable,
      filterData = FilterItemData.getStatusList()
    )
  }
}


enum class FilterGroupType {
  Selectable,
  Checkable,
  Switchable
}