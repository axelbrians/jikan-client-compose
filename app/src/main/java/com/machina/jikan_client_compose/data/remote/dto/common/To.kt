package com.machina.jikan_client_compose.data.remote.dto.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class To(
  @SerialName("day")
  val day: Int? = null,
  @SerialName("month")
  val month: Int? = null,
  @SerialName("year")
  val year: Int? = null
)