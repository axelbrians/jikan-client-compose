package com.machina.jikan_client_compose.presentation.home_screen.data

import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular

data class AnimeAiringPopularState(
  val data: List<AnimeAiringPopular> = listOf(),
  val isLoading: Boolean = false,
  val error: Event<String?> = Event(null)
)
