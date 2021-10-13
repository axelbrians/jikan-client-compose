package com.machina.jikan_client_compose.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.machina.jikan_client_compose.domain.model.ContentSearch

data class ContentSearchDto(
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

  /* Optional based on ContentType */

  // Anime
  @SerializedName("airing")
  val isAiring: Boolean?,

  @SerializedName("rated")
  val rated: String?,

  @SerializedName("episodes")
  val episodesCount: Int?,

  /*  Manga  */
  @SerializedName("publishing")
  val isPublishing: Boolean?,

  @SerializedName("type")
  val type: String?,

  @SerializedName("chapters")
  val chapters: Int?,

  @SerializedName("volumes")
  val volumes: Int?,

  /* - - - - - - - - - */


  @SerializedName("start_date")
  val startDate: String?,

  @SerializedName("end_date")
  val endDate: String?,

  @SerializedName("members")
  val members: Int,

  @SerializedName("score")
  val score: Double
)

fun ContentSearchDto.toAnimeSearch(): ContentSearch {
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


fun ContentSearchDto.toMangaSearch(): ContentSearch {
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