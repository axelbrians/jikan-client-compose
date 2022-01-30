package com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules


import com.machina.jikan_client_compose.data.remote.dto_v4.anime.Pagination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScheduleResponseV4(
  @SerialName("data")
  val `data`: List<AnimeScheduleDtoV4> = listOf(),
  @SerialName("pagination")
  val pagination: Pagination = Pagination()
)