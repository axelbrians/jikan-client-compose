package com.machina.jikan_client_compose.core.helper

import com.machina.jikan_client_compose.core.constant.AnimeGenres
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupType
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterItemData

object ParamHelper {

	fun parseGenreMapFilterToParamString(mapFilter: Map<String, FilterGroupData>): String {
		val tempFilter = mutableListOf<FilterItemData>()
		mapFilter.filterKeys {
			it.contains(AnimeGenres.GenreKey)
		}.map {
			tempFilter.addAll(it.value.filterData)
		}

		val filterGroupData = FilterGroupData(
			groupKey = AnimeGenres.GenreKey,
			groupName = "",
			isExpanded = false,
			type = FilterGroupType.Checkable,
			filterData = tempFilter
		)

		return parseFilterGroupDataToParamString(filterGroupData)
	}

	fun parseFilterGroupDataToParamString(filterGroupData: FilterGroupData): String {
		var contentRatingParam = ""
		filterGroupData.filterData.forEach { data ->
			if (data.isChecked) {
				contentRatingParam += "${data.key},"
			}
		}
		return contentRatingParam.dropLast(1)
	}
}