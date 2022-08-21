package com.machina.jikan_client_compose.presentation.content_search_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimeHorizontalModel

data class ContentSearchState(
  val data: AnimeHorizontalModel = AnimeHorizontalModel(),
  val isLoading: Boolean = false,
  val error: Event<String?> = Event(null)
) {
  companion object {
    val Loading = ContentSearchState(isLoading = true)
    val Initial = ContentSearchState()
  }
}