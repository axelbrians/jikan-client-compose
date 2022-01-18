package com.machina.jikan_client_compose.data.remote.dto.anime_top

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTopResponse(
  @SerialName("request_hash")
  val requestHash: String,

  @SerialName("request_cached")
  val requestCached: Boolean,

  @SerialName("request_cache_expiry")
  val requestCacheExpiry: Int,

  @SerialName("top")
  val top: List<AnimeTopDtoKtor>
)