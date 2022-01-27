package com.machina.jikan_client_compose.data.remote.dto.anime_top

import com.machina.jikan_client_compose.domain.model.anime.AnimeTop
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTopDtoKtor(
  @SerialName("mal_id")
  val malId: Int,

  @SerialName("rank")
  val rank: Int,

  @SerialName("title")
  val title: String,

  @SerialName("url")
  val url: String,

  @SerialName("image_url")
  val imageUrl: String,

  @SerialName("type")
  val type: String,

  @SerialName("episodes")
  val episodesCount: Int?,

  @SerialName("start_date")
  val startDate: String,

  @SerialName("end_date")
  val endDate: String?,

  @SerialName("members")
  val members: Int,

  @SerialName("score")
  val score: Double,
)

fun AnimeTopDtoKtor.toAnimeTop(): AnimeTop {
  return AnimeTop(
    malId, rank, title,
    url, imageUrl, type,
    episodesCount,
    members, score
  )
}