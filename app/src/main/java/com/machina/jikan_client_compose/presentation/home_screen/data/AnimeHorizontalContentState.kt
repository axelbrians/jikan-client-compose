package com.machina.jikan_client_compose.presentation.home_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel

data class AnimeHorizontalContentState(
  val data: List<AnimeVerticalModel> = listOf(),
  val isLoading: Boolean = false,
  val error: Event<String?> = Event(null)
) {

  fun isSuccess(): Boolean {
    return this.error.peekContent() == null
  }

  companion object {
    val Loading = AnimeHorizontalContentState(
      isLoading = true
    )

    fun from(state: AnimeScheduleState): AnimeHorizontalContentState {
      return AnimeHorizontalContentState(
        data = state.data.map { AnimeVerticalModel.from(it) },
        isLoading = state.isLoading,
        error = state.error
      )
    }

    fun from(state: AnimeTopState): AnimeHorizontalContentState {
      return AnimeHorizontalContentState(
        data = state.data.map { AnimeVerticalModel.from(it) },
        isLoading = state.isLoading,
        error = state.error
      )
    }
  }
}
