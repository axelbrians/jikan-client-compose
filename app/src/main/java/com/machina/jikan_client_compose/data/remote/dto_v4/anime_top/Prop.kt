package com.machina.jikan_client_compose.data.remote.dto_v4.anime_top


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Prop(
  @SerialName("from")
  val from: From = From(),
  @SerialName("to")
  val to: To = To(),
  @SerialName("string")
  val string: String = ""
)