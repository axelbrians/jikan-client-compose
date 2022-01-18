package com.machina.jikan_client_compose.data.remote.dto.anime_schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScheduleDto(
  @SerialName("request_hash")
  val requestHash: String = "",

  @SerialName("request_cached")
  val requestCached: Boolean = false,

  @SerialName("request_cache_expiry")
  val requestCacheExpiry: Int = 0,

  @SerialName("monday")
  val monday: List<AnimeScheduleDtoData> = listOf(),

  @SerialName("tuesday")
  val tuesday: List<AnimeScheduleDtoData> = listOf(),

  @SerialName("wednesday")
  val wednesday: List<AnimeScheduleDtoData> = listOf(),

  @SerialName("thursday")
  val thursday: List<AnimeScheduleDtoData> = listOf(),

  @SerialName("friday")
  val friday: List<AnimeScheduleDtoData> = listOf(),

  @SerialName("saturday")
  val saturday: List<AnimeScheduleDtoData> = listOf(),

  @SerialName("sunday")
  val sunday: List<AnimeScheduleDtoData> = listOf()

)