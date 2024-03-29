package com.machina.jikan_client_compose.data.remote.dto.anime_schedules


import com.machina.jikan_client_compose.data.remote.dto.anime_common.Pagination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScheduleResponseV4(
  @SerialName("pagination")
  val pagination: Pagination = Pagination.Empty,
  @SerialName("data")
  val data: List<AnimeScheduleDtoV4> = listOf()
)