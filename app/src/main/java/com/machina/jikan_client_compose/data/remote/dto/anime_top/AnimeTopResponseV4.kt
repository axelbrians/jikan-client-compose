package com.machina.jikan_client_compose.data.remote.dto.anime_top


import com.machina.jikan_client_compose.data.remote.dto.anime_common.Pagination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTopResponseV4(
  @SerialName("data")
  val data: List<AnimeTopDtoV4> = listOf(),
  @SerialName("pagination")
  val pagination: Pagination = Pagination()
)