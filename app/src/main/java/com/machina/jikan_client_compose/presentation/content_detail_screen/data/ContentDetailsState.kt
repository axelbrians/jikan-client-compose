package com.machina.jikan_client_compose.presentation.content_detail_screen.data

import com.machina.jikan_client_compose.domain.model.ContentDetails

data class ContentDetailsState(
  val data: ContentDetails? = null,
  val isLoading: Boolean = false,
  val error: String? = null
) {
  companion object {
    val Loading = ContentDetailsState(isLoading = true)
  }
}