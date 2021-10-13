package com.machina.jikan_client_compose.presentation.home_screen

import com.machina.jikan_client_compose.domain.model.ContentSearch

data class ContentSearchState(
  val data: List<ContentSearch> = listOf(),
  val isLoading: Boolean = false,
  val error: String? = null
)