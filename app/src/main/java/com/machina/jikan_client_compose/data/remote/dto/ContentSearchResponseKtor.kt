package com.machina.jikan_client_compose.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentSearchResponseKtor(
  @SerialName("request_hash")
  val requestHash: String,

  @SerialName("request_cached")
  val requestCached: Boolean,

  @SerialName("request_cache_expiry")
  val requestCacheExpiry: Int,

  @SerialName("last_page")
  val lastPage: Int,

  @SerialName("results")
  val results: List<ContentSearchDtoKtor>
)