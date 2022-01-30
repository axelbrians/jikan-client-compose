package com.machina.jikan_client_compose.domain.model.anime

import com.machina.jikan_client_compose.data.remote.dto.anime_schedule.*
import kotlinx.serialization.SerialName

data class AnimeSchedule(
  val malId: Int = 0,

  val url: String = "",

  val title: String = "",

  val imageUrl: String = "",

  val score: Double = 0.0,

  val rank: Int = 0
)