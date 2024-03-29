package com.machina.jikan_client_compose.presentation.content_detail_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimeCharacterModel

data class AnimeCharacterListState(
  val data: List<AnimeCharacterModel> = emptyList(),
  val isLoading: Boolean = false,
  val error: Event<String?> = Event(null)
) {
  companion object {
    val Loading = AnimeCharacterListState(isLoading = true)
  }
}
