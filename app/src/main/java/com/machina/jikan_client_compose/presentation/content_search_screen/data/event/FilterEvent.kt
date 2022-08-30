package com.machina.jikan_client_compose.presentation.content_search_screen.data.event

import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData

sealed class FilterEvent {
	/**
	 * Event for when a filter is Changed on certain [FilterGroupData]
	 */
	class FilterChanged(val filter: FilterGroupData) : FilterEvent()

	/**
	 * Event for when a filter is applied
	 */
	class FilterApplied(val contentType: ContentType, val query: String) : FilterEvent()

	/**
	 * Event for when a filter is reset to it's original state
	 */
	class FilterReset(val contentType: ContentType, val query: String) : FilterEvent()
}
