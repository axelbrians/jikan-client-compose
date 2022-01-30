package com.machina.jikan_client_compose.data.remote.dto_v4.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
  @SerialName("last_visible_page")
  val lastVisiblePage: Int = 0,
  @SerialName("has_next_page")
  val hasNextPage: Boolean = false
)