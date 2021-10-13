package com.machina.jikan_client_compose.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.machina.jikan_client_compose.domain.model.AnimeTop

data class AnimeTopDto(
  @SerializedName("mal_id")
  val malId: Int,

  @SerializedName("rank")
  val rank: Int,

  @SerializedName("title")
  val title: String,

  @SerializedName("url")
  val url: String,

  @SerializedName("image_url")
  val imageUrl: String,

  @SerializedName("type")
  val type: String,

  @SerializedName("episodes")
  val episodesCount: Int,

  @SerializedName("start_date")
  val startDate: String,

  @SerializedName("end_date")
  val endDate: String,

  @SerializedName("members")
  val members: Int,

  @SerializedName("score")
  val score: Double,
)

fun AnimeTopDto.toAnimeTop(): AnimeTop {
  return AnimeTop(
    malId, rank, title, url, imageUrl, type, episodesCount, startDate, endDate, members, score
  )
}