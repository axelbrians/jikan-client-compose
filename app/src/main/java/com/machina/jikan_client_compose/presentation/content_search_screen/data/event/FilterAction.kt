package com.machina.jikan_client_compose.presentation.content_search_screen.data.event

import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData

sealed interface FilterAction {
	/**
	 * Event for when a filter is Changed on certain [FilterGroupData]
	 */
	data class Change(val filter: FilterGroupData) : FilterAction

	/**
	 * Event for when a filter is applied
	 */
	data class Apply(val contentType: ContentType, val query: String) : FilterAction

	/**
	 * Event for when a filter is reset to it's original state
	 */
	data class Reset(val contentType: ContentType, val query: String) : FilterAction

	/**
	 * Event for fetching available Search Filter
	 */
	data class GetOption(val type: ContentType = ContentType.Anime): FilterAction
}
