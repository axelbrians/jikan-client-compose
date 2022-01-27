package com.machina.jikan_client_compose.data.remote.dto_v4.anime_top


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Licensor(
  @SerialName("mal_id")
  val malId: Int = 0,
  @SerialName("type")
  val type: String = "",
  @SerialName("name")
  val name: String = "",
  @SerialName("url")
  val url: String = ""
)