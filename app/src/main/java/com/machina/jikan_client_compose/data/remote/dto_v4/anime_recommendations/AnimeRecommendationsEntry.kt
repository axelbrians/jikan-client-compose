package com.machina.jikan_client_compose.data.remote.dto_v4.anime_recommendations

import com.machina.jikan_client_compose.data.remote.dto_v4.common.Images
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRecommendationsEntry(
  @SerialName("mal_id")
  val malId: Int = 0,
  @SerialName("url")
  val url: String = "",
  @SerialName("images")
  val images: Images = Images(),
  @SerialName("title")
  val title: String = "",
)
