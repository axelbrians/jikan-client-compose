package com.machina.jikan_client_compose.data.remote.dto_v4.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDataListWrapper<T>(
  @SerialName("data")
  val data: List<T>
)
