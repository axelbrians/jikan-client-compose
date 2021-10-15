package com.machina.jikan_client_compose.presentation.detail_screen.data

import com.machina.jikan_client_compose.domain.model.AnimeTop
import com.machina.jikan_client_compose.domain.model.ContentDetails

data class ContentDetailsState(
  val data: ContentDetails? = null,
  val isLoading: Boolean = false,
  val error: String? = null
)