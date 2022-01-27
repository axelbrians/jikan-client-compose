package com.machina.jikan_client_compose.presentation.home_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimeSchedule

data class AnimeScheduleState(
  val data: List<AnimeSchedule> = listOf(),
  val isLoading: Boolean = false,
  val error: Event<String?> = Event(null)
)