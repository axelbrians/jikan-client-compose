package com.machina.jikan_client_compose.data.remote.dto_v4.anime_top


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTopResponseV4(
  @SerialName("data")
  val `data`: List<AnimeTopDtoV4> = listOf(),
  @SerialName("pagination")
  val pagination: Pagination = Pagination()
)