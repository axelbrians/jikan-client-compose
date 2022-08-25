package com.machina.jikan_client_compose.core.helper

import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData

object ParamHelper {

	fun parseFilterGroupDataToParamString(filterGroupData: FilterGroupData): String {
		var contentRatingParam = ""
		filterGroupData.filterData.forEachIndexed { index, data ->
			if (data.isChecked) {
				contentRatingParam += data.key
				if (contentRatingParam.isNotEmpty() && index + 1 != filterGroupData.filterData.size) {
					contentRatingParam += ","
				}
			}
		}
		return contentRatingParam
	}
}