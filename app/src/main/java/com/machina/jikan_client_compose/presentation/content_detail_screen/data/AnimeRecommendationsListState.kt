package com.machina.jikan_client_compose.presentation.content_detail_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel

data class AnimeRecommendationsListState(
	val data: List<AnimePortraitDataModel> = emptyList(),
	val isLoading: Boolean = false,
	val error: Event<String?> = Event(null)
) {
  companion object {
    val Loading = AnimeRecommendationsListState(isLoading = true)
  }
}
