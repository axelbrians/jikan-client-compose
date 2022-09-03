package com.machina.jikan_client_compose.data.remote.dto.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExplicitGenre(
  @SerialName("mal_id")
  val malId: Int = 0,
  @SerialName("type")
  val type: String = "",
  @SerialName("name")
  val name: String = "",
  @SerialName("url")
  val url: String = ""
)

fun ExplicitGenre.toGenre(): Genre {
  return Genre(malId, type, name, url)
}