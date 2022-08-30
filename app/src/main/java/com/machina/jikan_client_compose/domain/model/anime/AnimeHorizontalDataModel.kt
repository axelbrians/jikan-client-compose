package com.machina.jikan_client_compose.domain.model.anime

import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.common.Jpg.Companion.getHighestResImgUrl

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
    fun from(data: AnimeDetailsDtoV4): AnimeHorizontalDataModel {
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