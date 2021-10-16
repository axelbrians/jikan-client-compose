package com.machina.jikan_client_compose.data.remote.dto

import com.machina.jikan_client_compose.domain.model.ContentSearch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentSearchDtoKtor(
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

  /* Optional based on ContentType */

  // Anime
  @SerialName("airing")
  val isAiring: Boolean? = null,

  @SerialName("rated")
  val rated: String? = null,

  @SerialName("episodes")
  val episodesCount: Int? = null,

  /*  Manga  */
  @SerialName("publishing")
  val isPublishing: Boolean? = null,

  @SerialName("type")
  val type: String? = null,

  @SerialName("chapters")
  val chapters: Int? = null,

  @SerialName("volumes")
  val volumes: Int? = null,

  /* - - - - - - - - - */


  @SerialName("start_date")
  val startDate: String? = null,

  @SerialName("end_date")
  val endDate: String? = null,

  @SerialName("members")
  val members: Int,

  @SerialName("score")
  val score: Double
)

fun ContentSearchDtoKtor.toAnimeModel(): ContentSearch {
  return ContentSearch(
    malId = malId,
    title = title,
    url = url,
    imageUrl = imageUrl,
    synopsis = synopsis,
    type = type,
    isAiring = isAiring,
    rated = rated,
    episodesCount = episodesCount,
    startDate = startDate,
    endDate = endDate,
    members = members,
    score = score
  )
}


fun ContentSearchDtoKtor.toMangaModel(): ContentSearch {
  return ContentSearch(
    malId = malId,
    title = title,
    url = url,
    imageUrl = imageUrl,
    synopsis = synopsis,
    type = type,
    isPublishing = isPublishing,
    chapters = chapters,
    volumes = volumes,
    startDate = startDate,
    endDate = endDate,
    members = members,
    score = score
  )
}
