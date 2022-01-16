package com.machina.jikan_client_compose.domain.model

import com.machina.jikan_client_compose.data.remote.dto.content_details.Genre
import kotlinx.serialization.SerialName

data class ContentDetails(
  val malId: Int,
  val url: String,
  val title: String,
  val titleEnglish: String,
  val titleSynonyms: List<String>,
  val titleJapanese: String,
  val status: String,
  val imageUrl: String,
  val type: String,


  /*  Manga  */
  val isPublishing: Boolean? = null,
  val chapters: Int? = null,
  val volumes: Int? = null,

  /* - - - - - - - - - */

  /*  Anime  */
  val episodeCount: Int? = null,
  val isAiring: Boolean? = null,
  val duration: String? = null,
  val ageRating: String? = null,
  val broadcast: String? = null,

  /* - - - - - - - - - */
  val genres: List<Genre>,
  val rank: Int,
  val score: Double,
  val scoredBy: Int,
  val popularity: Int,
  val members: Int,
  val favorites: Int,
  val synopsis: String,
  val background: String?,
)