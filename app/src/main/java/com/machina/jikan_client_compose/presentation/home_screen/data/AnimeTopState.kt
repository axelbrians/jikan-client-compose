package com.machina.jikan_client_compose.presentation.home_screen.data

import com.machina.jikan_client_compose.domain.model.AnimeTop

data class AnimeTopState(
  val data: List<AnimeTop> = listOf(),
  val isLoading: Boolean = false,
  val error: String? = null
)