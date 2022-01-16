package com.machina.jikan_client_compose.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeSearch(
  @SerialName("mal_id")
  val malId: Int,

  @SerialName("title")
  val title: String,

  @SerialName("url")
  val url: String,

  @SerialName("image_url")
  val imageUrl: String,

  @SerialName("synopsis")
  val synopsis: String,

  @SerialName("airing")
  val isAiring: Boolean,

  @SerialName("rated")
  val rated: String?,

  @SerialName("episodes")
  val episodesCount: Int,

  @SerialName("start_date")
  val startDate: String?,

  @SerialName("end_date")
  val endDate: String?,

  @SerialName("members")
  val members: Int,

  @SerialName("score")
  val score: Double
)
