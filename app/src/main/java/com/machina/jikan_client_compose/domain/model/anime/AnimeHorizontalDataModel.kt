package com.machina.jikan_client_compose.domain.model.anime

import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.common.Jpg.Companion.getHighestResImgUrl

data class AnimeHorizontalDataModel(
  val malId: Int,
  val title: String,
  val url: String,
  val imageUrl: String,
  val synopsis: String,
  val score: Double,
  val episodesCount: Int
) {
  companion object {
    fun from(data: AnimeDetailsDto): AnimeHorizontalDataModel {
      return AnimeHorizontalDataModel(
        malId = data.malId,
        title = data.title,
        url = data.url,
        imageUrl = data.images.jpg.getHighestResImgUrl(),
        synopsis = data.synopsis,
        score = data.score ?: 0.0,
        episodesCount = data.episodes
      )
    }
  }
}