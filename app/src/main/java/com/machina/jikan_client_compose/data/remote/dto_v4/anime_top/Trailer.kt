package com.machina.jikan_client_compose.data.remote.dto_v4.anime_top


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trailer(
  @SerialName("youtube_id")
  val youtubeId: String? = null,
  @SerialName("url")
  val url: String? = null,
  @SerialName("embed_url")
  val embedUrl: String? = null
)