package com.machina.jikan_client_compose.data.remote.dto_v4.anime_recommendations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRecommendationsResponse(
  @SerialName("entry")
  val entry: AnimeRecommendationsEntry = AnimeRecommendationsEntry(),
  @SerialName("url")
  val url: String = "",
  @SerialName("votes")
  val votes: Int = 0
)
