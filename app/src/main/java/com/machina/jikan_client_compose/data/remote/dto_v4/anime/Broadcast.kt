package com.machina.jikan_client_compose.data.remote.dto_v4.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Broadcast(
  @SerialName("day")
  val day: String? = null,
  @SerialName("time")
  val time: String? = null,
  @SerialName("timezone")
  val timezone: String? = null,
  @SerialName("string")
  val string: String? = null
)