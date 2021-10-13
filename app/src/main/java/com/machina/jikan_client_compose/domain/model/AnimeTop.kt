package com.machina.jikan_client_compose.domain.model

import com.google.gson.annotations.SerializedName

data class AnimeTop(
  val malId: Int,
  val rank: Int,
  val title: String,
  val url: String,
  val imageUrl: String,
  val type: String,
  val episodesCount: Int,
  val startDate: String,
  val endDate: String,
  val members: Int,
  val score: Double,
)
