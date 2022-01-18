package com.machina.jikan_client_compose.data.remote.dto.content_details

import com.machina.jikan_client_compose.data.remote.dto.content_details.Genre
import com.machina.jikan_client_compose.domain.model.ContentDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailsDto(

  @SerialName("request_hash")
  val requestHash: String,

  @SerialName("request_cached")
  val requestCached: Boolean,

  @SerialName("request_cache_expiry")
  val requestCacheExpiry: Int,

  @SerialName("mal_id")
  val malId: Int,

  @SerialName("url")
  val url: String,

  @SerialName("image_url")
  val imageUrl: String = "",

  @SerialName("title")
  val title: String,

  @SerialName("title_english")
  val titleEnglish: String = "",

  @SerialName("title_japanese")
  val titleJapanese: String = "",

  @SerialName("title_synonyms")
  val titleSynonyms: List<String> = listOf(),

  @SerialName("type")
  val type: String = "",

  @SerialName("status")
  val status: String = "",

  @SerialName("rank")
  val rank: Int,

  @SerialName("score")
  val score: Double,

  @SerialName("scored_by")
  val scoredBy: Int,

  @SerialName("popularity")
  val popularity: Int,

  @SerialName("members")
  val members: Int,

  @SerialName("favorites")
  val favorites: Int,

  @SerialName("synopsis")
  val synopsis: String,

  @SerialName("background")
  val background: String?,

  @SerialName("genres")
  val genres: List<Genre> = listOf(),


  /*  Manga  */
  @SerialName("publishing")
  val isPublishing: Boolean? = null,

  @SerialName("chapters")
  val chapters: Int? = null,

  @SerialName("volumes")
  val volumes: Int? = null,

  /* - - - - - - - - - */


  /*  Anime  */
  @SerialName("trailer_url")
  val trailerUrl: String? = null,

  @SerialName("episodes")
  val episodeCount: Int? = null,

  @SerialName("airing")
  val isAiring: Boolean? = null,

  @SerialName("duration")
  val duration: String? = null,

  @SerialName("rating")
  val ageRating: String? = null,

  @SerialName("broadcast")
  val broadcast: String? = null,

  /* - - - - - - - - - */
)

fun ContentDetailsDto.toAnimeModel(): ContentDetails {
  return ContentDetails(
    malId = malId,
    url = url,
    title = title,
    titleEnglish = titleEnglish,
    titleSynonyms = titleSynonyms,
    titleJapanese = titleJapanese,
    status = status,
    imageUrl = imageUrl,
    type = type,

    /* Anime Specific */
    episodeCount = episodeCount,
    isAiring = isAiring,
    duration = duration,
    ageRating = ageRating,
    broadcast = broadcast,

    genres = genres,
    rank = rank,
    score = score,
    scoredBy = scoredBy,
    popularity = popularity,
    members = members,
    favorites = favorites,
    synopsis = synopsis,
    background = background,
  )
}

fun ContentDetailsDto.toMangaModel(): ContentDetails {
  return ContentDetails(
    malId = malId,
    url = url,
    title = title,
    titleEnglish = titleEnglish,
    titleSynonyms = titleSynonyms,
    titleJapanese = titleJapanese,
    status = status,
    imageUrl = imageUrl,
    type = type,

    /* Manga specific */
    isPublishing = isPublishing,
    chapters = chapters,
    volumes = volumes,

    genres = genres,
    rank = rank,
    score = score,
    scoredBy = scoredBy,
    popularity = popularity,
    members = members,
    favorites = favorites,
    synopsis = synopsis,
    background = background,
  )
}