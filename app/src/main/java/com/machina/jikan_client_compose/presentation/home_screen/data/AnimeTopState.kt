package com.machina.jikan_client_compose.presentation.home_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimeTop

data class AnimeTopState(
  val data: List<AnimeTop> = listOf(),
  val isLoading: Boolean = false,
  val error: Event<String?> = Event(null)
) {
  companion object {
    val Loading = AnimeTopState(isLoading = true)
  }
}