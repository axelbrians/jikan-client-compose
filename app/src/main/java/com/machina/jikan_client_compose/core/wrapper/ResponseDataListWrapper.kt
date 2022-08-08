package com.machina.jikan_client_compose.core.wrapper

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDataListWrapper<T>(
  @SerialName("data")
  val data: List<T>
)
