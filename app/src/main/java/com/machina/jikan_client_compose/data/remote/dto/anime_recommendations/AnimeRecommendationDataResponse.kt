package com.machina.jikan_client_compose.data.remote.dto.anime_recommendations

import com.machina.jikan_client_compose.data.remote.dto.common.Images
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRecommendationDataResponse(
  @SerialName("mal_id")
  val malId: Int = 0,
  @SerialName("url")
  val url: String = "",
  @SerialName("images")
  val images: Images = Images(),
  @SerialName("title")
  val title: String = "",
)
