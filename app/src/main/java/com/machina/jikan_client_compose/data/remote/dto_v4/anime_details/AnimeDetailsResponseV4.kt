package com.machina.jikan_client_compose.data.remote.dto_v4.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetailsResponseV4(
  @SerialName("data")
  val `data`: AnimeDetailsDtoV4 = AnimeDetailsDtoV4()
)