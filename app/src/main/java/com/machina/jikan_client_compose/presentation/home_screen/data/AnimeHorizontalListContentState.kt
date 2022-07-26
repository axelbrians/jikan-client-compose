package com.machina.jikan_client_compose.presentation.home_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel

data class AnimeHorizontalListContentState(
  val data: AnimeVerticalModel = AnimeVerticalModel(),
  val isLoading: Boolean = false,
  val error: Event<String?> = Event(null)
) {

  fun isSuccess(): Boolean {
    return this.error.peekContent() == null
  }

  companion object {
    val Initial = AnimeHorizontalListContentState()

    val Loading = AnimeHorizontalListContentState(
      isLoading = true
    )
  }
}
