package com.machina.jikan_client_compose.domain.model.anime

import com.machina.jikan_client_compose.data.remote.dto.anime_common.Pagination

data class AnimeVerticalModel(
	val data: List<AnimePortraitDataModel> = listOf(),
	val pagination: Pagination = Pagination.Default
)
