package com.machina.jikan_client_compose.data.remote.dto.anime_recommendations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRecommendationResponse(
  @SerialName("entry")
  val entry: AnimeRecommendationDataResponse = AnimeRecommendationDataResponse(),
  @SerialName("url")
  val url: String = "",
  @SerialName("votes")
  val votes: Int = 0
)
