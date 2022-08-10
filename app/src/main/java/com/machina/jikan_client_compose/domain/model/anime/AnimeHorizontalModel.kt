package com.machina.jikan_client_compose.domain.model.anime

import com.machina.jikan_client_compose.data.remote.dto_v4.anime.Pagination

data class AnimeHorizontalModel(
  val data: List<AnimeHorizontalDataModel> = listOf(),
  val pagination: Pagination = Pagination.Default
)
