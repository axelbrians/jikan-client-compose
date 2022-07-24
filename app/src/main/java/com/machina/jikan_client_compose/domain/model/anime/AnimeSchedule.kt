package com.machina.jikan_client_compose.domain.model.anime


data class AnimeSchedule(
  val malId: Int = 0,
  val url: String = "",
  val title: String = "",
  val imageUrl: String = "",
  val score: Double = 0.0,
  val rank: Int = 0
)