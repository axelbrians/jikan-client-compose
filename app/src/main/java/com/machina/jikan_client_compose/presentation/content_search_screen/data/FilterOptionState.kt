package com.machina.jikan_client_compose.presentation.content_search_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData

data class FilterOptionState(
	val data: Map<String, FilterGroupData> = mapOf(),
	val isLoading: Boolean = false,
	val error: Event<String?> = Event(null)
) {
	companion object {
		val Loading = FilterOptionState(isLoading = true)
	}
}
