package com.machina.jikan_client_compose.data.remote.dto.anime_schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Theme(
  @SerialName("mal_id")
  val malId: Int = 0,
  @SerialName("type")
  val type: String = "",
  @SerialName("name")
  val name: String = "",
  @SerialName("url")
  val url: String = ""
)