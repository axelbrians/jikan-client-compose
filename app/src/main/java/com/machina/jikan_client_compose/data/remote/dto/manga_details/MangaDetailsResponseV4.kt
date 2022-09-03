package com.machina.jikan_client_compose.data.remote.dto.manga_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MangaDetailsResponseV4(
  @SerialName("data")
  val `data`: MangaDetailsDtoV4 = MangaDetailsDtoV4()
)