package com.machina.jikan_client_compose.core.wrapper

import com.machina.jikan_client_compose.data.remote.dto.anime_common.Pagination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDataListWrapper<T>(
  @SerialName("pagination")
  val pagination: Pagination = Pagination(),
  @SerialName("data")
  val data: List<T>
)
