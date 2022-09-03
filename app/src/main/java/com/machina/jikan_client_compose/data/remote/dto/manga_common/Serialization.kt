package com.machina.jikan_client_compose.data.remote.dto.manga_common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Serialization(
  @SerialName("mal_id")
  val malId: Int = 0,
  @SerialName("type")
  val type: String = "",
  @SerialName("name")
  val name: String = "",
  @SerialName("url")
  val url: String = ""
)