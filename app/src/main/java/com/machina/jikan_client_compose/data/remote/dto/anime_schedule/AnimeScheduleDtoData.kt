package com.machina.jikan_client_compose.data.remote.dto.anime_schedule


import com.machina.jikan_client_compose.domain.model.anime.AnimeSchedule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScheduleDtoData(
  @SerialName("mal_id")
  val malId: Int = 0,

  @SerialName("url")
  val url: String = "",

  @SerialName("title")
  val title: String = "",

  @SerialName("image_url")
  val imageUrl: String = "",

  @SerialName("synopsis")
  val synopsis: String = "",

  @SerialName("type")
  val type: String? = null,

  @SerialName("airing_start")
  val airingStart: String = "",

  @SerialName("episodes")
  val episodes: Int? = null,

  @SerialName("members")
  val members: Int = 0,

  @SerialName("genres")
  val genres: List<Genre> = listOf(),

  @SerialName("explicit_genres")
  val explicitGenres: List<ExplicitGenre> = listOf(),

  @SerialName("themes")
  val themes: List<Theme> = listOf(),

  @SerialName("demographics")
  val demographics: List<Demographic> = listOf(),

  @SerialName("source")
  val source: String = "",

  @SerialName("producers")
  val producers: List<Producer> = listOf(),

  @SerialName("score")
  val score: Double? = null,

//  @SerialName("licensors")
//  val licensors: List<Any> = listOf(),

  @SerialName("r18")
  val r18: Boolean = false,

  @SerialName("kids")
  val kids: Boolean = false
)

fun AnimeScheduleDtoData.toAnimeSchedule(): AnimeSchedule {
  return AnimeSchedule(
    malId,
    url,
    title,
    imageUrl,
    episodes,
    members,
    demographics,
    score,
    r18,
    kids
  )
}