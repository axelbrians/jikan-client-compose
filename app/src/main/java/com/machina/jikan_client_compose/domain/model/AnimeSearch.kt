package com.machina.jikan_client_compose.domain.model

import com.google.gson.annotations.SerializedName

data class AnimeSearch(
  @SerializedName("mal_id")
  val malId: Int,

  @SerializedName("title")
  val title: String,

  @SerializedName("url")
  val url: String,

  @SerializedName("image_url")
  val imageUrl: String,

  @SerializedName("synopsis")
  val synopsis: String,

  @SerializedName("airing")
  val isAiring: Boolean,

  @SerializedName("rated")
  val rated: String?,

  @SerializedName("episodes")
  val episodesCount: Int,

  @SerializedName("start_date")
  val startDate: String?,

  @SerializedName("end_date")
  val endDate: String?,

  @SerializedName("members")
  val members: Int,

  @SerializedName("score")
  val score: Double
)
