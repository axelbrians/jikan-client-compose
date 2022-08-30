package com.machina.jikan_client_compose.presentation.content_search_screen.data.event

import com.machina.jikan_client_compose.core.enums.ContentType

sealed class SearchEvent {
	class SearchFirstPage(val contentType: ContentType, val query: String): SearchEvent()
	class SearchNextPage(val contentType: ContentType, val query: String): SearchEvent()
}
