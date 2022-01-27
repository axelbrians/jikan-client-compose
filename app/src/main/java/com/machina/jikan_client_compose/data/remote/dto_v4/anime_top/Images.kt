package com.machina.jikan_client_compose.data.remote.dto_v4.anime_top


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Images(
  @SerialName("jpg")
  val jpg: Jpg = Jpg(),
  @SerialName("webp")
  val webp: Webp = Webp()
)